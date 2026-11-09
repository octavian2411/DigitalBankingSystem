const BASE_URL = 'http://localhost:8081/api';

async function request(path, options = {}) {
  const response = await fetch(BASE_URL + path, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  const body = await response.json().catch(() => null);
  if (!response.ok) {
    throw new Error(body?.error || `Request failed (${response.status})`);
  }
  return body;
}

export const api = {
  getCustomers: () => request('/customers'),
  getAccounts: (customerId) => request(`/customers/${customerId}/accounts`),
  getNotifications: (customerId) => request(`/customers/${customerId}/notifications`),
  openAccount: (customerId, type, initialDeposit) =>
    request('/accounts', { method: 'POST', body: JSON.stringify({ customerId, type, initialDeposit }) }),
  changeStatus: (accountId, status) =>
    request(`/accounts/${accountId}/status`, { method: 'PATCH', body: JSON.stringify({ status }) }),
  getHistory: (accountId) => request(`/accounts/${accountId}/transactions`),
  transfer: (sourceAccountId, targetAccountId, amount) =>
    request('/transfers', { method: 'POST', body: JSON.stringify({ sourceAccountId, targetAccountId, amount }) }),
  getAllTransactions: (status) => request(`/admin/transactions${status ? `?status=${status}` : ''}`),
};
