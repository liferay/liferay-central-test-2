/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.spring.transaction;

import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionAttribute.Builder;
import com.liferay.portal.kernel.transaction.TransactionLifecycleManager;
import com.liferay.portal.kernel.transaction.TransactionStatus;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public abstract class BaseTransactionExecutor implements TransactionExecutor {

	protected TransactionAttribute createTransactionAttribute(
		org.springframework.transaction.interceptor.TransactionAttribute
			transactionAttribute) {

		Builder builder = new Builder();

		builder.setIsolation(
			Isolation.getIsolation(transactionAttribute.getIsolationLevel()));
		builder.setPropagation(
			Propagation.getPropagation(
				transactionAttribute.getPropagationBehavior()));
		builder.setReadOnly(transactionAttribute.isReadOnly());

		return builder.build();
	}

	protected TransactionStatus createTransactionStatus(
		org.springframework.transaction.TransactionStatus transactionStatus) {

		return new TransactionStatus(
			transactionStatus.isNewTransaction(),
			transactionStatus.isRollbackOnly(),
			transactionStatus.isCompleted());
	}

	protected void fireTransactionCommittedEvent(
		org.springframework.transaction.interceptor.TransactionAttribute
			transactionAttribute,
		org.springframework.transaction.TransactionStatus transactionStatus) {

		TransactionLifecycleManager.fireTransactionCommittedEvent(
			createTransactionAttribute(transactionAttribute),
			createTransactionStatus(transactionStatus));
	}

	protected void fireTransactionCreatedEvent(
		org.springframework.transaction.interceptor.TransactionAttribute
			transactionAttribute,
		org.springframework.transaction.TransactionStatus transactionStatus) {

		TransactionLifecycleManager.fireTransactionCreatedEvent(
			createTransactionAttribute(transactionAttribute),
			createTransactionStatus(transactionStatus));
	}

	protected void fireTransactionRollbackedEvent(
		org.springframework.transaction.interceptor.TransactionAttribute
			transactionAttribute,
		org.springframework.transaction.TransactionStatus transactionStatus,
		Throwable throwable) {

		TransactionLifecycleManager.fireTransactionRollbackedEvent(
			createTransactionAttribute(transactionAttribute),
			createTransactionStatus(transactionStatus), throwable);
	}

}