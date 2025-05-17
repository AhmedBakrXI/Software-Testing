# Software Testing Project Phase II - Team 10

**Course**: CSE337s - Software Testing
**Faculty**: Faculty of Engineering, Ain-Shams University
**Repository**: [Software-Testing on GitHub](https://github.com/AhmedBakrXI/Software-Testing)

## Team Members

* Ahmed Mohammed Bakr Ahmed (2000037)
* Ahmed Khaled Abd El Maksod Ebrahim (2000218)
* Adham Khaled Abd El Maqsoud Ali (2000066)
* Eslam Mohamed Marzouk Abdelaziz (2000252)
* Karim Ibrahim Saad Abd-Elrazek Elshehawy (2001118)
* Marwan Wael Mahmoud Abbas (2001244)

---

## Table of Contents

1. [White Box Testing](#white-box-testing)

   * [Test Coverage](#test-coverage)
   * [FileReader class](#filereader-class)
   * [FileWriter class](#filewriter-class)
   * [GenreBasedRecommendation class](#genrebasedrecommendation-class)
   * [MovieRecommendationService class](#movierecommendationservice-class)
   * [Movie class](#movie-class)
   * [User class](#user-class)
   * [ValidationService class](#validationservice-class)
2. [Black Box Testing](#black-box-testing)

   * [Boundary Value Analysis](#boundary-value-analysis)
   * [Equivalence Partitioning](#equivalence-partitioning)
3. [Integration Testing](#integration-testing)

   * [Big Bang Testing](#big-bang-testing)
   * [Bottom-Up Testing](#bottom-up-testing)
4. [Data Flow](#data-flow)

   * [GenreBasedRecommendation Class](#dataflow-genrebasedrecommendation-class)
   * [FileReader Class](#dataflow-filereader-class)
   * [FileWriter Class](#dataflow-filewriter-class)
   * [ValidationService Class](#dataflow-validationservice-class)
   * [MovieRecommendationService Class](#dataflow-movierecommendationservice-class)

---

## White Box Testing

### Test Coverage

Detailed coverage metrics and branch descriptions for all classes and methods.

### FileReader class

* Scenarios for reading valid and empty user/movie files.
* Test cases: FRWB-01 to FRWB-04.

### FileWriter class

* Scenarios for writing to files under different conditions.
* Test cases: FWB-01 to FWB-06.

### GenreBasedRecommendation class

* Scenarios covering recommendation logic: single/multiple favourites, exclusions, duplicates.
* Test cases: BWB-01 to BWB-07.

### MovieRecommendationService class

* Generation of recommendations for users under valid and edge conditions.
* Test cases: WBS-01 to WBS-03.

### Movie class

* Equality, `toString()`, and reflexivity tests for Movie objects.
* Test cases: MWB-01 to MWB-08.

### User class

* Equality and `toString()` tests for User objects.
* Test cases: UWB-01 to UWB-08.

### ValidationService class

* Validation logic for users and movies, including format checks and uniqueness constraints.
* Test cases: TC32 to TC75.

## Black Box Testing

### Boundary Value Analysis

* Validation scenarios for user names, IDs, movie titles, IDs, and genres under boundary conditions.
* Test cases: BVA-01 to BVA-16.

### Equivalence Partitioning

* Partition-based test scenarios for input classes.
* Test cases: EP-01 to EP-28.

## Integration Testing

### Big Bang Testing

* End-to-end integration of validation and recommendation modules.
* Scenarios BBG-1 to BBG-7.

### Bottom-Up Testing

* Incremental integration starting from core components up to service.
* Scenarios BU-1 to BU-8.

## Data Flow

### GenreBasedRecommendation Class

* Variable definitions and uses covering all-uses coverage.

### FileReader Class

* Read-users and read-movies method flows.

### FileWriter Class

* Flow through `writeToFile()` under various conditions.

### ValidationService Class

* Validation sub-method flows for users and movies.

### MovieRecommendationService Class

* Flow through `generateRecommendations()`.

---
