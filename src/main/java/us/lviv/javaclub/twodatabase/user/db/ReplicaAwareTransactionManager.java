package us.lviv.javaclub.twodatabase.user.db;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

class ReplicaAwareTransactionManager implements PlatformTransactionManager {
  private final PlatformTransactionManager wrapped;

  ReplicaAwareTransactionManager(@NonNull final PlatformTransactionManager wrapped) {
    this.wrapped = wrapped;
  }

  @Override
  public TransactionStatus getTransaction(@Nullable final TransactionDefinition definition) throws TransactionException {
    boolean isTxActive = TransactionSynchronizationManager.isActualTransactionActive();

    if (definition == null) {
      throw new CannotCreateTransactionException("Can not request RW transaction");
    }

    if (isTxActive && TransactionRoutingDataSource.isCurrentlyReadonly() && !definition.isReadOnly()) {
      throw new CannotCreateTransactionException("Can not request RW transaction from initialized readonly transaction");
    }

    if (!isTxActive) {
      TransactionRoutingDataSource.setReadonlyDataSource(definition.isReadOnly());
    }

    return wrapped.getTransaction(definition);
  }

  @Override
  public void commit(@NonNull final TransactionStatus status) throws TransactionException {
    wrapped.commit(status);
  }

  @Override
  public void rollback(@NonNull final TransactionStatus status) throws TransactionException {
    wrapped.rollback(status);
  }
}
