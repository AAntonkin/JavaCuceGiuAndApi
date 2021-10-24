Feature: By Lead Secret endpoint verification

  @api
  Scenario: By Lead Secret endpoint
    When User make Post call to 'By Lead Secret' endpoint with such data:
      | Header | x-cf-source-id  | coding-challenge                     |
      | Header | x-cf-corr-id    | <random_uuid>                        |
      | Header | Content-Type    | application/json                     |
      | Body   | loanAppUuid     | b8096ec7-2150-405f-84f5-ae99864b3e96 |
      | Body   | skipSideEffects | true                                 |
    Then User see response status from 'By Lead Secret' endpoint is 200
    And User see response from 'By Lead Secret' endpoint contains such data in any place:
      | productType   | PERSONAL_LOAN  |
      | desiredAmount | 25000.0        |
      | maskedEmail   | .*@upgrade.com  |
    When User make Post call to 'By Lead Secret' endpoint with such data:
      | Header | x-cf-source-id  | coding-challenge |
      | Header | x-cf-corr-id    | <random_uuid>    |
      | Header | Content-Type    | application/json |
      | Body   | loanAppUuid     | bad-uuid-2345    |
      | Body   | skipSideEffects | true             |
    Then User see response status from 'By Lead Secret' endpoint is 500