CREATE TABLE ADDRESS(
    ID BIGINT AUTO_INCREMENT NOT NULL COMMENT 'Идентификатор',
    POSTCODE VARCHAR(6) COMMENT 'Индекс',
    CITY VARCHAR(100) NOT NULL COMMENT 'Город',
    STREET VARCHAR(100) NOT NULL COMMENT 'Улица',
    HOUSE VARCHAR(50) NOT NULL COMMENT 'Дом',
    APARTMENT VARCHAR(50) COMMENT 'Квартира',
    PRIMARY KEY (ID)
) ENGINE=MYISAM COMMENT 'Таблица для сохранения адресов';

CREATE TABLE COMPANY(
    ID BIGINT AUTO_INCREMENT NOT NULL COMMENT 'Идентификатор',
    NAME VARCHAR(100) UNIQUE NOT NULL COMMENT 'Название компании',
    LEGAL_FORM TINYINT(1) NOT NULL COMMENT 'Форма компании: 0 - ООО, 1 - ИП, 2 - АО',
    REGISTRATION_DATE DATE NOT NULL COMMENT 'Дата регистрации компании',
    ADDRESS_ID BIGINT NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT FK_ADDRESS_COMPANY FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ID)
) ENGINE=MYISAM COMMENT 'Таблица для сохранения компаний';

CREATE TABLE BRANCH(
    ID BIGINT AUTO_INCREMENT NOT NULL COMMENT 'Идентификатор',
    NAME VARCHAR(100) NOT NULL COMMENT 'Название филиала',
    REGISTRATION_DATE DATE NOT NULL COMMENT 'Дата регистрации филиала компании',
    ADDRESS_ID BIGINT NOT NULL,
    COMPANY_ID BIGINT NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT FK_ADDRESS_BRANCH FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ID),
    CONSTRAINT FK_COMPANY_BRANCH FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID),
    UNIQUE (NAME, COMPANY_ID)
) ENGINE=MYISAM COMMENT 'Таблица для сохранения филиалов компании';
