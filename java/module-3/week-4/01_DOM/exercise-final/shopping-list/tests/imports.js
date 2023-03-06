const { JSDOM } = require("jsdom");
const rewire = require("rewire");
const mocha = require("mocha");
const chai = require("chai");

// Set up loader to be used in tests.js file
const tryGet = (variableName, module) => {
  try {
    return module.__get__(variableName);
  } catch {
    console.error(`Missing ${variableName}`);
  }
};

const documentLoader = async () => {
  let jsdom;
  if (typeof document === "undefined") {
    jsdom = await JSDOM.fromFile("./shopping-list/index.html", {
      runScripts: "dangerously",
    });
    await new Promise((resolve) =>
      jsdom.window.addEventListener("load", resolve)
    );

    document = jsdom.window.document;
    const testCode = rewire("./shopping-list/js/shopping-list.js");
    groceries = tryGet("groceries", testCode);
    markCompleted = tryGet("markCompleted", testCode);
  }
};
