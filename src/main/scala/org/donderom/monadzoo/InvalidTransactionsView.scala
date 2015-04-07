package org.donderom.monadzoo

import akka.persistence._
import TransactionActorProtocol._

class InvalidTransactionsView extends PersistentView {
  override def persistenceId: String = "transactions"
  override def viewId: String = "invalid-transactions-view"

  var invalidTransactions: List[Transaction] = Nil

  def receive = {
    case Event(transaction) if transaction.status == Invalid =>
      invalidTransactions = transaction :: invalidTransactions
    case Print => println(invalidTransactions)
  }
}
