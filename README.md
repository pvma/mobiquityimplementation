# Packer Implementation
A parser of items for packages that get the most priciest based from a list that can be put into a package.

## Prerequsites
1. Java 11
2. Maven 3
3. Eclipse IDE 2020-03

## Installation
1. Clone or download ZIP File here `git@github.com:pvma/packerimplementation.git`
2. Import as existing source in Eclipse IDE

## How to Test
- Open `src/main/test/*.java` and run to see what it does
- Primarily this is used to parse a file, but you can use the test classes to get your needed information

#### Example Input
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
8 : (1,15.3,€34)
75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)
56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)
```

#### Example Output
```
4
-
2,7
8,9
```

## Notes
- File input needs to be UTF-8
- Output is String
- Jar file generated name is packerimplementation