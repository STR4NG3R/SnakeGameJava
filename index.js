const testReverse = (text) => {
  let res = ''
  for (let i = text.length - 1; i >= 0; --i) {
    const car = text[i]
    res += car
  }
  return res
}


console.log(testReverse('aabbb'))
console.log(testReverse('a'))
console.log(testReverse('abcdef'))

const fact = (number) => number === 1 ? 1 : fact(number - 1) + number

console.log(fact(3))
