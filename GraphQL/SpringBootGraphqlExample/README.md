# Spring Boot with GraphQL Query Example

Here is the sample example  to demonstrate usage of GraphQL for exposing data to specified HTTP endpoint or URL 

# Book Store

/rest/books is the REST resource which can fetch Books information

DataFetchers are Interfaces for RuntimeWiring of GraphQL with JpaRepository

# Sample GraphQL Scalar Queries

Accessible under http://localhost:8091/rest/books

Usage for allBooks { allBooks { isn title authors publisher } }

Usage for book { book(id: "123") { title authors publisher }

Combination of both allBooks and book { allBooks { title authors } book(id: "124") { title authors publisher } }
