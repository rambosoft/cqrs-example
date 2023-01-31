# Playing around with Event Sourcing and CQRS
### The main available features in this projects are:
* Users are able to open a bank account using a `POST` request to `/account`, and each bank account begins with an initial deposit which can be specified as a part of the request body.
* Each payment might be a credit `POST` `/transaction/credit/{accountId}` or debit `POST` `/transaction/debit/{accountId}` payment, consisting of booking amount.
* Credit and debit payments affect the bank account by either increasing or decreasing the account balance. There is no need to consider overdrafts at this point, i.e., if payment is released, the account balance gets debited or credited accordingly.
* It is possible to retrieve the current account balance of a given bank account using a `GET` request `/account/{accountId}`.
* It is possible to get a list of all transactions booked of a given bank account since a given calendar date by calling `GET` `/account/{accountId}/transactions`.
* It should be possible to receive a list of all bank accounts in the red, i.e., whose account balance is lower than zero by calling `GET` `/account/red`.