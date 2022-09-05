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

const fibonnaci = (number) => number <= 0 ? 0 : number <= 2 ? 1 : fibonnaci(number - 2) + fibonnaci(number - 1)

console.log(fibonnaci(9))

const fibbo = (number) => {
  if (number <= 0) return 0
  let first = 0, second = 1;
  while (--number) {
    const value = first + second
    first = second
    second = value
  }
  return second
}

console.log(fibbo(3))
