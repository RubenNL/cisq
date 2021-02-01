// Simple Node.js script for generating a word import SQL-script
// edited to fit in 10.000 limit of heroku
const { readFileSync, writeFileSync } = require("fs");

const LINGO_WORDS = /^[a-z]{5,7}$/;
const words = readFileSync(`${__dirname}/woorden-opentaal.txt`, "utf-8")
    .split("\n")
    .filter(word => LINGO_WORDS.test(word))
    .sort(() => 0.5 - Math.random())
    .map(word => `('${word}', ${word.length})`);

const sql="-- Extracted from: https://github.com/OpenTaal/opentaal-wordlist/\n"
    + `INSERT INTO words (word, length) VALUES \n\t${words.slice(0, 6000).join(",\n\t")};`;

writeFileSync(`${__dirname}/../../src/main/resources/data.sql`, sql);
