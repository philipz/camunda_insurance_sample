import {
  Client,
  logger,
  Variables,
  File,
} from "camunda-external-task-client-js";

// configuration for the Client:
//  - 'baseUrl': url to the Process Engine
//  - 'logger': utility to automatically log important events
const config = {
  baseUrl: "http://localhost:8080/engine-rest",
  use: logger,
};

// create a Client instance with custom configuration
const client = new Client(config);

// susbscribe to the topic: 'invoiceCreator'
client.subscribe("insuranceContractProvider", async function ({ task, taskService }) {
  // Put your business logic
  // complete the task

  // Create variables to be returned to the process
  console.log("開始準備保單");
  const premium = task.variables.get("premium");
  const contractMessage = prepareInsuranceContract(premium);
  const processVariables = new Variables();
  processVariables.set("contractMessage", contractMessage);

  await taskService.complete(task, processVariables);
});

function prepareInsuranceContract(premium) {
  return "恭喜！核保通過，保費為每年：" + premium + "。";
}

client.subscribe("declineMessageProvider", async function ({ task, taskService }) {
  // Put your business logic
  // complete the task

  // Create variables to be returned to the process
  console.log("開始準備拒保通知");
  const declineMessage = prepareDeclineMessage();
  const processVariables = new Variables();
  processVariables.set("declineMessage", declineMessage);

  await taskService.complete(task, processVariables);
});

function prepareDeclineMessage() {
  return "抱歉！保險申請被拒絕，請聯繫客服中心以便獲得更詳細資訊。";
}
