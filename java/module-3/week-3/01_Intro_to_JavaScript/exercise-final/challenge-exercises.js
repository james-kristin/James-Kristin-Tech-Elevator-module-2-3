/**
 *
 * @param {String} input Space delimited string of numbers
 */
 function iqTest(valsStr) {
  if (valsStr == '') {
      return 0;
  }

  let vals = valsStr.split(" ");
  let numEvens = 0;
  let numOdds = 0;
  let evenPos = -1;
  let oddPos = -1;

  for (let i = 0; i < vals.length; ++i) {
      if (vals[i] % 2 == 0) {
          numEvens++;
          evenPos = i + 1;
      } else {
          numOdds++;
          oddPos = i + 1;
      }
  }

  if (numEvens == 1) {
      return evenPos;
  } else if (numOdds == 1) {
      return oddPos;
  }

  return 0;
}

/**
 * Title cases a list of strings.
 * @param {String} title String to convert
 * @param {String} exceptionWords Space delimited words to ignore
 */
function titleCase(title, exceptionWords) {
  const exceptions = exceptionWords === undefined ? [] : exceptionWords.toLowerCase().split(' ');
  let words = title.toLowerCase().split(' ');

  for (let i = 0; i < words.length; i++) {
    if (!exceptions.includes(words[i]) || i === 0) {
      words[i] = words[i].charAt(0).toUpperCase() + words[i].slice(1);
    }
  }

  return words.join(' ');
}
