package org.donderom.monadzoo

import akka.persistence._

object TransactionActorProtocol {
  sealed trait Status
  case object Completed extends Status
  case object Invalid extends Status

  case class Transaction(account: String, amount: BigDecimal, status: Status)

  case class Command(transaction: Transaction)
  case class Event(transaction: Transaction)
  case object SaveSnapshot
  case object Print
}

class TransactionActor extends PersistentActor {
  import TransactionActorProtocol._

  case class State(transactions: List[Transaction] = Nil) {
    def updated(event: Event): State = copy(event.transaction :: transactions)
  }

  override def persistenceId = "transactions"

  def updateState(event: Event): Unit = state = state.updated(event)

  var state = State()

  val receiveRecover: Receive = {
    case event: Event => updateState(event)
    case SnapshotOffer(_, snapshot: State) => state = snapshot
  }

  val receiveCommand: Receive = {
    case Command(transaction) => persist(Event(transaction))(updateState)
    case SaveSnapshot => saveSnapshot(state)
    case Print => println(state)
  }
}
