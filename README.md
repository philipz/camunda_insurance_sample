---
SN: 20231101001
Name: Camunda
version: 7.20.0
create_date: 2023-11-01
update_date: 2023-11-01
---

# Camunda 7 保單流程範例

從[Camunda工作流開發實戰](https://www.tenlong.com.tw/products/9787302569428)書中的範例，修改成繁體中文，並更新到7.20.0版本，並加上process-test-coverage流程涵蓋測試。

## 範例教學安裝需求

### 硬體需求：

無

### 軟體需求：

* Java開發環境

### 其他需求：

無

## 使用指南

Camunda Spring Boot Application.

This project has been generated by the Maven archetype
[camunda-archetype-spring-boot-7.20.0](https://docs.camunda.org/manual/7.20/user-guide/).

### Show me the important parts!
[BPMN Process](src/main/resources/insurance.bpmn)

![BPMN Process](/src/main/resources/insurance.png)

### How does it work?

### How to use it?

### Unit Test
You can run the JUnit test in your IDE or using:
```bash
mvn clean test
```

### Running the application
You can also build and run the process application with Spring Boot.

#### Manually
1. Build the application using:
```bash
mvn clean package
```
2. Run the *.jar file from the `target` directory using:
```bash
java -jar target/insurance.jar
```

#### Your Java IDE
1. Run the project as a Java application in your IDE using CamundaApplication as the main class.

2. Start process.

`curl -X POST -H "Content-Type: application/json" localhost:8080/engine-rest/message -d @request_message.json`

3. Receive contract.

`curl -X POST -H "Content-Type: application/json" localhost:8080/engine-rest/message -d @contract_message.json`

### Run and Inspect with Tasklist and Cockpit
Once you deployed the application you can run it using
[Camunda Tasklist](http://docs.camunda.org/latest/guides/user-guide/#tasklist)
and inspect it using
[Camunda Cockpit](http://docs.camunda.org/latest/guides/user-guide/#cockpit).

### Environment Restrictions
Built and tested against Camunda BPM version 7.20.0.

### License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

<!-- HTML snippet for index page
  <tr>
    <td><img src="snippets/insurance/src/main/resources/process.png" width="100"></td>
    <td><a href="snippets/insurance">Camunda Spring Boot Application</a></td>
    <td>Spring Boot Application using [Camunda](http://docs.camunda.org).</td>
  </tr>
-->
<!-- Tweet
New @Camunda example: Camunda Spring Boot Application - Spring Boot Application using [Camunda](http://docs.camunda.org). https://github.com/camunda-consulting/code/tree/master/snippets/insurance
-->

## 延伸閱讀或額外資源

* [bilibili線上課程](https://www.bilibili.com/video/BV1qe4y1m7D7/)