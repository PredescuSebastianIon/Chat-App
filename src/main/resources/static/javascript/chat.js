const chatId = window.location.pathname.split('/').pop();
const myUserIdAttr = document.body.getAttribute('data-my-user-id');
const myUserId = myUserIdAttr ? Number(myUserIdAttr) : null;

function appendMessage(m) {
    const ul = document.getElementById('messages');
    const li = document.createElement('li');
    if (myUserId && Number(m.userId) === myUserId) li.classList.add('me'); else li.classList.add('other');
    li.innerHTML = `<span class="text">${m.content}</span><span class="time">${new Date(m.createdAt).toLocaleTimeString()}</span>`;
    const nearBottom = (ul.scrollHeight - ul.scrollTop - ul.clientHeight) < 120;
    ul.appendChild(li);
    if (nearBottom) li.scrollIntoView({ behavior: 'smooth', block: 'end' });
}

// REST: istoric
fetch(`/api/chats/${chatId}/messages?page=0&size=50`, { credentials: 'same-origin' })
    .then(r => r.json())
    .then(page => page.content.forEach(appendMessage))
    .catch(() => { document.getElementById('status').textContent = 'Eroare la încărcarea istoricului'; });

// WS: live
const stomp = Stomp.over(new SockJS('/websockets'));
stomp.debug = null;
function setStatus(t, ok = true) { const s = document.getElementById('status'); s.textContent = t; s.style.color = ok ? '#94a3b8' : '#ef4444'; }
stomp.connect({}, () => {
    setStatus('Conectat');
    stomp.subscribe(`/topic/chats/${chatId}`, (frame) => appendMessage(JSON.parse(frame.body)));
}, () => setStatus('Deconectat', false));

// send
document.getElementById('send').addEventListener('click', () => {
    const input = document.getElementById('msg');
    const text = (input.value || '').trim();
    if (!text) return;
    stomp.send(`/app/chat/${chatId}/send`, {}, JSON.stringify({ content: text }));
    input.value = ''; input.focus();
});
document.getElementById('msg').addEventListener('keydown', (e) => { if (e.key === 'Enter') document.getElementById('send').click(); });
