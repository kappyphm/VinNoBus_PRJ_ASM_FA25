/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  kappyphm
 * Created: Sep 24, 2025
 */

USE master;
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'ASM_PRJ')
BEGIN
    ALTER DATABASE [ASM_PRJ] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE [ASM_PRJ];
END;
GO

-- ==============================================
-- T?o Database ASM_PRJ
-- ==============================================
CREATE DATABASE ASM_PRJ;
GO

USE ASM_PRJ;
GO

-- ==============================================
-- B?ng Ng??i dùng (User)
-- Ch?a thông tin chung cho t?t c? lo?i ng??i dùng trong h? th?ng
-- role: phân quy?n (ADMIN, MANAGER, DRIVER, CONDUCTOR, CUSTOMER)
-- status: tr?ng thái tài kho?n
-- ==============================================
CREATE TABLE [User] (
    user_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(200),
    role VARCHAR(20) CHECK (role IN ('ADMIN','MANAGER','DRIVER','CONDUCTOR','CUSTOMER')),
    status VARCHAR(10) CHECK (status IN ('ACTIVE','INACTIVE'))
);

-- ==============================================
-- B?ng Khách hàng (Customer)
-- Thông tin chi ti?t c?a khách hàng (m? r?ng t? User)
-- Liên k?t 1-1 v?i b?ng User qua user_id
-- ==============================================
CREATE TABLE [Customer](
	user_id UNIQUEIDENTIFIER PRIMARY KEY REFERENCES [User](user_id),
	address NVARCHAR(255) NOT NULL,
	phone VARCHAR(15) NOT NULL UNIQUE,
	email VARCHAR(254) NOT NULL UNIQUE,
	avatar_url VARCHAR(500)
);

-- ==============================================
-- B?ng Xe buýt (Bus)
-- M?i xe có bi?n s? (unique), s?c ch?a, tr?ng thái hi?n t?i
-- current_status l?y theo enum ??nh ngh?a
-- ==============================================
CREATE TABLE Bus (
    bus_id INT IDENTITY(1,1) PRIMARY KEY,
    plate_number VARCHAR(50) NOT NULL UNIQUE,
    capacity INT NOT NULL CHECK (capacity > 0),
    --current_status VARCHAR(20) CHECK (current_status IN ('AVAILABLE','IN_USE','MAINTENANCE','BROKEN','REPAIRING','RESERVED'))
);

-- ==============================================
-- B?ng L?ch s? xe buýt (BusLog)
-- L?u l?i tr?ng thái thay ??i c?a xe theo th?i gian
-- created_by: ng??i dùng thao tác thay ??i
-- ==============================================
CREATE TABLE BusLog (
    log_id INT IDENTITY(1,1) PRIMARY KEY,
    bus_id INT NOT NULL,
    status VARCHAR(20) CHECK (status IN ('AVAILABLE','IN_USE','MAINTENANCE','BROKEN','REPAIRING','RESERVED')),
    note VARCHAR(255),
    created_by UNIQUEIDENTIFIER NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_BusLog_Bus FOREIGN KEY (bus_id) REFERENCES Bus(bus_id),
    CONSTRAINT FK_BusLog_User FOREIGN KEY (created_by) REFERENCES [User](user_id)
);

-- ==============================================
-- B?ng Tr?m xe buýt (Station)
-- Danh sách các tr?m d?ng trong h? th?ng
-- ==============================================
CREATE TABLE Station (
    station_id INT IDENTITY(1,1) PRIMARY KEY,
    station_name VARCHAR(150) NOT NULL,
    location VARCHAR(255) -- TODO: sau này có th? thay b?ng t?a ?? GPS
);

-- ==============================================
-- B?ng Tuy?n xe (Route)
-- M?i tuy?n xe có tên, lo?i tuy?n (ROUND_TRIP/CIRCULAR), t?n su?t
-- Không l?u start_point và end_point ?? tránh trùng v?i Route_Station
-- ==============================================
CREATE TABLE Route (
    route_id INT IDENTITY(1,1) PRIMARY KEY,
    route_name VARCHAR(150) NOT NULL,
	type VARCHAR(10) CHECK (type in ('ROUND_TRIP', 'CIRCULAR')),
    frequency INT CHECK (frequency >= 0)
);

-- ==============================================
-- B?ng Quan h? Route ? Station
-- Xác ??nh tuy?n xe ?i qua nh?ng tr?m nào và th? t? d?ng
-- station_order: th? t? tr?m trên tuy?n
-- ==============================================
CREATE TABLE Route_Station (
    route_id INT NOT NULL,
    station_id INT NOT NULL,
    station_order INT CHECK (station_order >= 1),
    PRIMARY KEY (route_id, station_id),
    CONSTRAINT FK_RS_Route FOREIGN KEY (route_id) REFERENCES Route(route_id),
    CONSTRAINT FK_RS_Station FOREIGN KEY (station_id) REFERENCES Station(station_id)
);

-- ==============================================
-- B?ng Chuy?n xe (Trip)
-- M?i chuy?n xe g?n v?i m?t tuy?n, m?t xe buýt, m?t tài x? và m?t ph? xe
-- departure_time, arrival_time: gi? kh?i hành và k?t thúc
-- status: tr?ng thái chuy?n (NOT_STARTED, IN_PROCESS, FINISHED, CANCELLED)
-- ==============================================
CREATE TABLE Trip (
    trip_id INT IDENTITY(1,1) PRIMARY KEY,
    route_id INT NOT NULL,
    bus_id INT NOT NULL,
    departure_time TIME NOT NULL,   -- gi? b?t ??u trong ngày
    arrival_time TIME NOT NULL,     -- gi? k?t thúc trong ngày
    status VARCHAR(20) DEFAULT 'NOT_STARTED' 
        CHECK (status IN ('NOT_STARTED','IN_PROCESS','FINISHED','CANCELLED')),

    CONSTRAINT FK_Trip_Route FOREIGN KEY (route_id) REFERENCES Route(route_id),
    CONSTRAINT FK_Trip_Bus FOREIGN KEY (bus_id) REFERENCES Bus(bus_id)
);


CREATE TABLE Shift (
    shift_id INT IDENTITY(1,1) PRIMARY KEY,
    trip_id INT NOT NULL,
    work_date DATE NOT NULL,   -- ngày áp d?ng ca
    driver_id UNIQUEIDENTIFIER NOT NULL,
    conductor_id UNIQUEIDENTIFIER NOT NULL,
    status VARCHAR(20) DEFAULT 'SCHEDULED'
        CHECK (status IN ('SCHEDULED','IN_PROCESS','FINISHED','CANCELLED')),

    CONSTRAINT FK_Shift_Trip FOREIGN KEY (trip_id) REFERENCES Trip(trip_id),
    CONSTRAINT FK_Shift_Driver FOREIGN KEY (driver_id) REFERENCES [User](user_id),
    CONSTRAINT FK_Shift_Conductor FOREIGN KEY (conductor_id) REFERENCES [User](user_id),
    CONSTRAINT UQ_Shift UNIQUE (trip_id, work_date)  -- 1 chuy?n / 1 ngày ch? có 1 ca
);


-- ==============================================
-- B?ng Nh?t ký công vi?c (WorkLog)
-- L?u l?i check-in/check-out c?a nhân viên trong chuy?n
-- Ch? áp d?ng cho role DRIVER và CONDUCTOR
-- Ràng bu?c: m?i chuy?n ch? có 1 driver + 1 conductor
-- ==============================================
CREATE TABLE WorkLog (
    worklog_id INT IDENTITY(1,1) PRIMARY KEY,
    trip_id INT NOT NULL,
    user_id UNIQUEIDENTIFIER NOT NULL,
    role VARCHAR(20) NOT NULL,
    checkin_time DATETIME NULL,
    checkout_time DATETIME NULL,
    notes NVARCHAR(255) NULL,

    CONSTRAINT FK_WorkLog_Trip FOREIGN KEY (trip_id) REFERENCES Trip(trip_id),
    CONSTRAINT FK_WorkLog_User FOREIGN KEY (user_id) REFERENCES [User](user_id),
    CONSTRAINT CK_WorkLog_Role CHECK (role IN ('DRIVER','CONDUCTOR')),
    CONSTRAINT UQ_WorkLog_TripRole UNIQUE (trip_id, role)
);

-- ==============================================
-- B?ng Hóa ??n (Invoice)
-- L?u thông tin thanh toán
-- M?t invoice có th? g?n v?i nhi?u ticket (1-n)
-- ==============================================
CREATE TABLE Invoice (
    invoice_id INT IDENTITY(1,1) PRIMARY KEY,
    payment_method VARCHAR(20) CHECK (payment_method IN ('CASH','ONLINE')),
    payment_date DATETIME DEFAULT GETDATE(),
    status VARCHAR(10) CHECK (status IN ('PAID','PENDING'))
);

-- ==============================================
-- B?ng Vé (Ticket)
-- Vé có th? g?n v?i trip (vé l??t) ho?c route (vé ngày/tháng)
-- expiry_date b?t bu?c v?i vé ngày/tháng, NULL v?i vé l??t
-- invoice_id: liên k?t hóa ??n thanh toán
-- ==============================================
CREATE TABLE Ticket (
    ticket_id INT IDENTITY(1,1) PRIMARY KEY,
    customer_id UNIQUEIDENTIFIER NULL,
    trip_id INT NULL,
    route_id INT NULL,
    price DECIMAL(18,0) NOT NULL,
    issue_date DATE NOT NULL,
    expiry_date DATE NULL,
    created_by UNIQUEIDENTIFIER NOT NULL,
    invoice_id INT NULL,

    CONSTRAINT FK_Ticket_Customer FOREIGN KEY (customer_id) REFERENCES [User](user_id),
    CONSTRAINT FK_Ticket_Trip FOREIGN KEY (trip_id) REFERENCES Trip(trip_id),
    CONSTRAINT FK_Ticket_Route FOREIGN KEY (route_id) REFERENCES Route(route_id),
    CONSTRAINT FK_Ticket_CreatedBy FOREIGN KEY (created_by) REFERENCES [User](user_id),
    CONSTRAINT FK_Ticket_Invoice FOREIGN KEY (invoice_id) REFERENCES Invoice(invoice_id),

    -- Logic: Vé l??t thì có trip_id, vé ngày/tháng thì có route_id
    CONSTRAINT CK_Ticket_Validity CHECK (
        (trip_id IS NOT NULL AND route_id IS NULL AND expiry_date IS NULL) -- Vé l??t
        OR
        (trip_id IS NULL AND route_id IS NOT NULL AND expiry_date IS NOT NULL) --Vé ngày/tháng 
    )
);
