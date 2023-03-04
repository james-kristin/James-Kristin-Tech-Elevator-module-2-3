const chai = require("chai");
const rewire = require("rewire");
const exercises = rewire("./exercises.js");

const tryGet = (variableName) => {
  let result;
  try {
    result = exercises.__get__(variableName);
  } catch {
    console.error(`Missing ${variableName}`);
  }
  return result;
};

const isAdmitted = tryGet("isAdmitted");

const useParameterToFilterArray = tryGet("useParameterToFilterArray");

const makeNumber = tryGet("makeNumber");

const addAll = tryGet("addAll");

const makeHappy = tryGet("makeHappy");

const getFullAddressesOfProperties = tryGet("getFullAddressesOfProperties");

const findLargest = tryGet("findLargest");

const getSumOfSubArrayValues = tryGet("getSumOfSubArrayValues");
