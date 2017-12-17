DROP TABLE request_log IF EXISTS;
DROP TABLE request IF EXISTS;
DROP TABLE request_parameter IF EXISTS;

CREATE TABLE request_log (
  id         INTEGER IDENTITY PRIMARY KEY,
  request VARCHAR(9000),
  response  VARCHAR(9000),
  creator  VARCHAR(50),
  creation_date DATE,
  reference INTEGER
);

CREATE TABLE request (
  id         INTEGER IDENTITY PRIMARY KEY,
  action VARCHAR(100),
  provider  VARCHAR(100),
  merchant VARCHAR(100),
  parameters  VARCHAR(1000),
  request_content  VARCHAR(2000),
  request_string  VARCHAR(5000),
  request_parameters  VARCHAR(1000),
  password  VARCHAR(100),
  token  VARCHAR(100),
  security_code  VARCHAR(100),
  payment_number  VARCHAR(100),
  client_request_id  VARCHAR(100),
  external_reference_1  VARCHAR(100),
  external_reference_2  VARCHAR(100),
  external_reference_3  VARCHAR(100),
  log  VARCHAR(9000),
  type  VARCHAR(50),
  creator  VARCHAR(100),
  creation_date DATE,
  trace_number  VARCHAR(100),
  transaction_code  VARCHAR(100),
  response_error_code  VARCHAR(10),
  response_status  VARCHAR(100),
  response_message  VARCHAR(1000),
  response_entrance_reference  VARCHAR(10),
  response_technical_error_message  VARCHAR(1000),
  response_stack_trace  VARCHAR(9000)
 );

 CREATE TABLE request_parameter (
  id         INTEGER IDENTITY PRIMARY KEY,
  request_id INTEGER,
  name  VARCHAR(100),
  value  VARCHAR(500),
  parent  VARCHAR(100),
  type VARCHAR(50),
  creator VARCHAR(100),
  modified_by VARCHAR(100),
  creation_date DATE,
  modified_date DATE
);
