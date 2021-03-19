// Simple Node.js script for generating a word import SQL-script
// edited to make compatible with heroku
const { readFileSync, writeFileSync } = require("fs");

const LINGO_WORDS = /^[a-z]{5,7}$/;
wordsGrouped={}
readFileSync(`${__dirname}/woorden-opentaal.txt`, "utf-8")
	.split("\n")
	.filter(word => LINGO_WORDS.test(word))
	.forEach(word=>{
		if(!wordsGrouped[word.length]) wordsGrouped[word.length]=[];
		wordsGrouped[word.length].push(word)
	})
Object.keys(wordsGrouped).forEach(length=>{
	writeFileSync(`${__dirname}/../src/main/resources/words/${length}.txt`, wordsGrouped[length].join('\n'));
})
