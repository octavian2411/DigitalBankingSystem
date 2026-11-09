import { useEffect, useState } from 'react';
import { api } from './api.js';

function TransactionTable({ transactions }) {
  if (transactions.length === 0) return <p>No transactions.</p>;
  return (
    <table>
      <thead>
        <tr><th>ID</th><th>From</th><th>To</th><th>Amount</th><th>Fee</th><th>Status</th><th>Reason</th></tr>
      </thead>
      <tbody>
        {transactions.map((tx) => (
          <tr key={tx.id}>
            <td>{tx.id}</td>
            <td>{tx.sourceAccountId}</td>
            <td>{tx.targetAccountId}</td>
            <td>{tx.amount}</td>
            <td>{tx.fee}</td>
            <td className={tx.status}>{tx.status}</td>
            <td>{tx.reason}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

function CustomerView({ customerId }) {
  const [accounts, setAccounts] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [history, setHistory] = useState([]);
  const [transfer, setTransfer] = useState({ source: '', target: '', amount: '' });
  const [newAccount, setNewAccount] = useState({ type: 'CHECKING', deposit: '' });
  const [error, setError] = useState('');

  const reload = async () => {
    setAccounts(await api.getAccounts(customerId));
    setNotifications(await api.getNotifications(customerId));
    if (selectedAccount) setHistory(await api.getHistory(selectedAccount));
  };

  useEffect(() => {
    setSelectedAccount(null);
    setHistory([]);
    api.getAccounts(customerId).then(setAccounts);
    api.getNotifications(customerId).then(setNotifications);
  }, [customerId]);

  const run = async (action) => {
    setError('');
    try {
      await action();
      await reload();
    } catch (e) {
      setError(e.message);
    }
  };

  const showHistory = async (accountId) => {
    setSelectedAccount(accountId);
    setHistory(await api.getHistory(accountId));
  };

  const sendTransfer = (e) => {
    e.preventDefault();
    run(() => api.transfer(Number(transfer.source), Number(transfer.target), transfer.amount));
  };

  const openAccount = (e) => {
    e.preventDefault();
    run(() => api.openAccount(customerId, newAccount.type, newAccount.deposit || 0));
  };

  return (
    <>
      {error && <p className="error">{error}</p>}

      <section>
        <h3>Accounts</h3>
        <table>
          <thead>
            <tr><th>ID</th><th>IBAN</th><th>Type</th><th>Balance</th><th>Status</th><th></th></tr>
          </thead>
          <tbody>
            {accounts.map((a) => (
              <tr key={a.id}>
                <td>{a.id}</td>
                <td>{a.iban}</td>
                <td>{a.type}</td>
                <td>{a.balance}</td>
                <td>{a.status}</td>
                <td>
                  <button onClick={() => showHistory(a.id)}>History</button>
                  <button onClick={() => run(() => api.changeStatus(a.id, a.status === 'ACTIVE' ? 'FROZEN' : 'ACTIVE'))}>
                    {a.status === 'ACTIVE' ? 'Freeze' : 'Activate'}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <form onSubmit={openAccount} style={{ marginTop: 8 }}>
          <select value={newAccount.type} onChange={(e) => setNewAccount({ ...newAccount, type: e.target.value })}>
            <option value="CHECKING">Checking</option>
            <option value="SAVINGS">Savings</option>
          </select>
          <input placeholder="Initial deposit" value={newAccount.deposit}
                 onChange={(e) => setNewAccount({ ...newAccount, deposit: e.target.value })} />
          <button type="submit">Open account</button>
        </form>
      </section>

      <section>
        <h3>Transfer funds</h3>
        <form onSubmit={sendTransfer}>
          <select value={transfer.source} onChange={(e) => setTransfer({ ...transfer, source: e.target.value })}>
            <option value="">From account...</option>
            {accounts.map((a) => <option key={a.id} value={a.id}>{a.id} - {a.type}</option>)}
          </select>
          <input placeholder="Target account ID" value={transfer.target}
                 onChange={(e) => setTransfer({ ...transfer, target: e.target.value })} />
          <input placeholder="Amount" value={transfer.amount}
                 onChange={(e) => setTransfer({ ...transfer, amount: e.target.value })} />
          <button type="submit">Send</button>
        </form>
      </section>

      {selectedAccount && (
        <section>
          <h3>History for account {selectedAccount}</h3>
          <TransactionTable transactions={history} />
        </section>
      )}

      <section>
        <h3>Notifications</h3>
        {notifications.length === 0 ? <p>No notifications.</p> : (
          <ul>{notifications.map((n) => <li key={n.id}>{n.message}</li>)}</ul>
        )}
      </section>
    </>
  );
}

function AdminView() {
  const [status, setStatus] = useState('');
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    api.getAllTransactions(status).then(setTransactions);
  }, [status]);

  return (
    <section>
      <h3>All transactions</h3>
      <select value={status} onChange={(e) => setStatus(e.target.value)}>
        <option value="">All</option>
        <option value="PENDING">Pending</option>
        <option value="COMPLETED">Completed</option>
        <option value="REJECTED">Rejected</option>
      </select>
      <button onClick={() => api.getAllTransactions(status).then(setTransactions)}>Refresh</button>
      <TransactionTable transactions={transactions} />
    </section>
  );
}

export default function App() {
  const [customers, setCustomers] = useState([]);
  const [view, setView] = useState('');

  useEffect(() => {
    api.getCustomers().then((list) => {
      setCustomers(list);
      if (list.length > 0) setView(String(list[0].id));
    });
  }, []);

  return (
    <div>
      <h1>Digital Banking</h1>
      <label>
        View as:{' '}
        <select value={view} onChange={(e) => setView(e.target.value)}>
          {customers.map((c) => <option key={c.id} value={c.id}>{c.name}</option>)}
          <option value="admin">Administrator</option>
        </select>
      </label>
      <hr />
      {view === 'admin' ? <AdminView /> : view && <CustomerView customerId={Number(view)} />}
    </div>
  );
}
