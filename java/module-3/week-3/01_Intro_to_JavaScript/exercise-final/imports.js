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

const sumDouble = tryGet("sumDouble");

const hasTeen = tryGet("hasTeen");

const lastDigit = tryGet("lastDigit");

const seeColor = tryGet("seeColor");

const oddOnly = tryGet("oddOnly");

const frontAgain = tryGet("frontAgain");

const cigarParty = tryGet("cigarParty");

const fizzBuzz = tryGet("fizzBuzz");

const filterEvens = tryGet("filterEvens");

const filterBigNumbers = tryGet("filterBigNumbers");

const filterMultiplesOfX = tryGet("filterMultiplesOfX");

const createObject = tryGet("createObject");
