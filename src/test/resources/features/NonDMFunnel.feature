Feature: Base Test verification

  @gui
  Scenario: Searching for a term
    Given User is on 'Personal Loans And Cards' page
    When User enters such data in to fields on 'Personal Loans And Cards' page:
      | Desired Amount | 2,000            |
      | Loan Purpose   | Home Improvement |
    And User click 'Check Your Rate' button on 'Personal Loans And Cards' page
    And User select 'Individual' radio button on 'Lets Get Started With Basic Information' page
    And User enters such data in to fields on 'Lets Get Started With Basic Information' page:
      | First Name    | <random_first_name>     |
      | Last Name     | <random_last_name>      |
      | Home Address  | <random_street_address> |
      | City          | <random_city>           |
      | State         | OK                      |
      | Zip Code      | <random_zip_code>       |
      | Date Of Birth | <random_date_of_birth>  |
    And User click 'First Name' button on 'Lets Get Started With Basic Information' page
    And User click 'Continue' button on 'Lets Get Started With Basic Information' page
    And User enters such data in to fields on 'How Much Money Do You Make' page:
      | Individual Annual Income | 165000 |
      | Additional Annual Income | 8000   |
    And User click 'Continue' button on 'How Much Money Do You Make' page and wait for next page to appear
    And User enters such data in to fields on 'Last Step Before You Get Your Rate' page:
      | Email Address | <random_email>    |
      | Password      | <random_password> |
      | Agreement     | true              |
    And User click 'Check Your Rate' button on 'Last Step Before You Get Your Rate' page and wait for next page to appear
    Then User can see such values on 'Great News Here Are Your Offers' page:
      | Loan Amount     | <not_empty> |
      | Monthly Payment | <not_empty> |
      | Term            | <not_empty> |
      | Interest Rate   | <not_empty> |
      | APR             | <not_empty> |
    When User click 'Menu' button on 'Great News Here Are Your Offers' page
    And User click 'Sign Out' button on 'Great News Here Are Your Offers' page and wait for next page to appear
    Then User see that 'See You Later' page is opened
    When User is on 'Login' page
    And User enters such data in to fields on 'Login' page:
      | Email Address | <random_email>    |
      | Password      | <random_password> |
    And User click 'Sign In To Your Account' button on 'Login' page and wait for next page to appear
    Then User can see such values on 'Great News Here Are Your Offers' page:
      | Loan Amount     | <Loan Amount>     |
      | Monthly Payment | <Monthly Payment> |
      | Term            | <Term>            |
      | Interest Rate   | <Interest Rate>   |
      | APR             | <APR>             |
