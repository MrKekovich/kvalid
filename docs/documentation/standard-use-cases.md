# KValid Validation Rules

## String Validation Rules

| Rule                | Description                                                                                                                                  |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| notBlank            | Ensures that the string is not empty and does not consist solely of whitespace characters. Uses the underlying `String.isNotBlank` function. |
| ofLength            | Validates that the `String.length` is exactly equal to the specified length.                                                                 |
| ofLength (range)    | Checks if the `String.length` falls within the specified `IntRange`.                                                                         |
| notOfLength         | Ensures that the `String.length` is not equal to the specified length.                                                                       |
| notOfLength (range) | Validates that the `String.length` does not fall within the specified `IntRange`.                                                            |
| minLength           | Checks if the `String.length` is greater than or equal to the specified minimum length.                                                      |
| maxLength           | Ensures that the `String.length` is less than or equal to the specified maximum length.                                                      |
| matches             | Validates that the string matches the given `Regex` pattern using `Regex.matches(input)`.                                                    |
| matches (string)    | Validates that the string matches the given `Regex` pattern using `Regex.matches(input)`.                                                    |
| notMatches          | Ensures that the string does not match the given `Regex` pattern using `!Regex.matches(input)`.                                              |
| notMatches (string) | Ensures that the string does not match the given `Regex` pattern using `!Regex.matches(input)`.                                              |

## Collection Validation Rules

| Rule              | Description                                                                                                            |
|-------------------|------------------------------------------------------------------------------------------------------------------------|
| ofSize            | Validates that the `Collection.size` is exactly equal to the specified size.                                           |
| ofSize (range)    | Checks if the `Collection.size` falls within the specified `IntRange`.                                                 |
| notOfSize         | Ensures that the `Collection.size` is not equal to the specified size.                                                 |
| notOfSize (range) | Validates that the `Collection.size` does not fall within the specified `IntRange`.                                    |
| minSize           | Checks if the `Collection.size` is greater than or equal to the specified minimum size.                                |
| maxSize           | Ensures that the `Collection.size` is less than or equal to the specified maximum size.                                |
| contains          | Validates that the collection contains the specified element using `Collection.contains(element)`.                     |
| notContains       | Checks that the collection does not contain the specified element using `!Collection.contains(element)`.               |
| containsAllOf     | Ensures that the collection contains all the specified elements using `Collection.containsAll(elements)`.              |
| notContainsAllOf  | Validates that the collection does not contain all the specified elements using `!Collection.containsAll(elements)`.   |
| containsNoneOf    | Checks that the collection contains none of the specified elements using `elements.none { it in collection }`.         |
| allMatch          | Ensures that all elements in the collection satisfy the given predicate using `Collection.all(predicate)`.             |
| anyMatch          | Validates that at least one element in the collection satisfies the given predicate using `Collection.any(predicate)`. |
| noneMatch         | Checks that no elements in the collection satisfy the given predicate using `Collection.none(predicate)`.              |

## Comparable Validation Rules

| Rule                 | Description                                                                                                |
|----------------------|------------------------------------------------------------------------------------------------------------|
| equalTo              | Validates that the comparable value is equal to the specified value using `==` operator.                   |
| notEqualTo           | Ensures that the comparable value is not equal to the specified value using `!=` operator.                 |
| greaterThan          | Checks if the comparable value is greater than the specified minimum using `>` operator.                   |
| greaterThanOrEqualTo | Validates that the comparable value is greater than or equal to the specified minimum using `>=` operator. |
| lessThan             | Ensures that the comparable value is less than the specified maximum using `<` operator.                   |
| lessThanOrEqualTo    | Checks if the comparable value is less than or equal to the specified maximum using `<=` operator.         |