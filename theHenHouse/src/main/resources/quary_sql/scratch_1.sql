CREATE DATABASE hen_house;
USE DATABASE hen_house;
CREATE TABLE employee(
    employee_id      VARCHAR (6)PRIMARY KEY,
    employee_name    VARCHAR (19),
    employee_contact VARCHAR (13)UNIQUE,
    employee_address VARCHAR (50),
    employee_salary  DECIMAL (7,2)
);



CREATE TABLE attendance(
    employee_id VARCHAR (6) PRIMARY KEY,
    attendance_date DATE,
    attendance_type VARCHAR (10),
    CONSTRAINT FOREIGN KEY (employee_id) REFERENCES employee(employee_id)
);



CREATE TABLE user(
    user_mail VARCHAR (30)PRIMARY KEY,
    user_password VARCHAR (30)

);



CREATE TABLE customer(
    customer_id VARCHAR (6) PRIMARY KEY,
    customer_name VARCHAR (20) NOT NULL,
    customer_address VARCHAR (20)NOT NULL,
    customer_contact VARCHAR (13)UNIQUE

);



CREATE TABLE item(
    item_id VARCHAR (6) PRIMARY KEY,
    description VARCHAR(26)NOT NULL,
    unit_price DECIMAL(6,2)NOT NULL,
    qty_on_hand INT NOT NULL
);



CREATE TABLE shop_order(
     order_id VARCHAR(6),
     order_date DATE,
     customer_id VARCHAR (6),
     CONSTRAINT PRIMARY KEY(order_id,customer_id),
     CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE ON UPDATE CASCADE

);




CREATE TABLE shop_order_details(
    order_id VARCHAR (6),
    description VARCHAR (26)NOT NULL,
    item_id VARCHAR (6),
    qty INT,
    unit_price DECIMAL (6,2)NOT NULL,
    CONSTRAINT PRIMARY KEY(order_id,item_id),
    CONSTRAINT FOREIGN KEY (order_id) REFERENCES shop_order(order_id) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (item_id) REFERENCES item(item_id) ON DELETE CREATE ON UPDATE CASCADE
);

CREATE TABLE supplier(
    supplier_id VARCHAR(6)PRIMARY KEY,
    name VARCHAR(19)NOT NULL,
    contact VARCHAR(13)UNIQUE
);

CREATE TABLE received_item_detail(
    supplier_id      VARCHAR(6),
    received_item_id VARCHAR(6),
    qty              INT,
    amount           DECIMAL(8, 2),
    unitprice        DECIMAL (10),
    Date             DATE

);