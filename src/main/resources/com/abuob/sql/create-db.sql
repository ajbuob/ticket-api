
CREATE TABLE IF NOT EXISTS CUSTOMER(
  ID BIGINT generated by default as identity, -- java.lang.Long
  CUST_NAME varchar(255) NOT NULL, -- java.lang.String
  EMAIL varchar(255) NOT NULL, -- java.lang.String
  CITY varchar(255) NOT NULL, -- java.lang.String
  STATE varchar(255) NOT NULL, -- java.lang.String
  ZIP INT NOT NULL, --java.lang.Integer
  PRIMARY KEY (ID));

CREATE TABLE IF NOT EXISTS REFERRAL(
  ID BIGINT generated by default as identity, -- java.lang.Long
  CUSTOMER_ID BIGINT NOT NULL, -- java.lang.Long
  VISIT_DATE TIMESTAMP NOT NULL, -- java.util.Date
  SOURCE varchar(255) NOT NULL, -- java.lang.String
  PRIMARY KEY (ID),
  FOREIGN KEY (CUSTOMER_ID) references CUSTOMER(ID));

CREATE TABLE IF NOT EXISTS VENUE(
  ID BIGINT generated by default as identity, -- java.lang.Long
  VENUE_NAME varchar(255) NOT NULL, -- java.lang.String
  CITY varchar(255) NOT NULL, -- java.lang.String
  STATE varchar(255) NOT NULL, -- java.lang.String
  ZIP INT NOT NULL, --java.lang.Integer
  PRIMARY KEY (ID));

CREATE TABLE IF NOT EXISTS EVENT(
  ID BIGINT generated by default as identity, -- java.lang.Long
  DESCRIPTION varchar(255) NOT NULL, -- java.lang.String
  EVENT_DATE TIMESTAMP NOT NULL, -- java.util.Date
  CITY varchar(255) NOT NULL, -- java.lang.String
  STATE varchar(255) NOT NULL, -- java.lang.String
  ZIP INT NOT NULL, --java.lang.Integer
  PRIMARY KEY (ID));

CREATE TABLE IF NOT EXISTS VENUE_EVENT(
  ID BIGINT generated by default as identity, -- java.lang.Long
  VENUE_ID BIGINT NOT NULL, -- java.lang.Long
  EVENT_ID BIGINT NOT NULL, -- java.lang.Long
  PRIMARY KEY (ID),
  FOREIGN KEY (VENUE_ID) references VENUE(ID),
  FOREIGN KEY (EVENT_ID) references EVENT(ID));

CREATE TABLE IF NOT EXISTS INVENTORY(
    ID BIGINT generated by default as identity, -- java.lang.Long
    EVENT_ID BIGINT NOT NULL, -- java.lang.Long
    SECT INT NOT NULL, --java.lang.Integer
    QUANTITY INT NOT NULL, --java.lang.Integer
    PRICE DECIMAL(19,2) NOT NULL, --java.math.BigDecimal
    ROW varchar(10) NOT NULL, -- java.lang.String
    SELLER_ID BIGINT NOT NULL,
    BUYER_ID BIGINT,
    PRIMARY KEY (ID),
    FOREIGN KEY (SELLER_ID) references CUSTOMER(ID),
    FOREIGN KEY (BUYER_ID) references CUSTOMER(ID));