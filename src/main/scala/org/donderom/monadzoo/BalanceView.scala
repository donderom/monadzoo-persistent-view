package org.donderom.monadzoo

import akka.persistence._
import TransactionActorProtocol._

class BalanceView extends PersistentView {
  override def persistenceId: String = "transactions"
  override def viewId: String = "balance-view"

  var balance: Map[String, BigDecimal] = Map.empty

  def receive = {
    case Event(Transaction(account, amount, Completed)) =>
      balance = balance.updated(account, balance.getOrElse(account, BigDecimal(0)) + amount)
    case Print => println(balance)
  }
}
