document.addEventListener('click', async (e) => {
    if (!e.target.matches('.start-chat')) return;

    const receiverId = e.target.dataset.userId;
    try {
        const res = await fetch('/api/chats/direct', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ receiverId: Number(receiverId) })
        });
        if (!res.ok) throw new Error('Cannot start chat');
        const { chatId } = await res.json();
        window.location.href = `/chat/${chatId}`;
    } catch (err) {
        console.error(err);
        alert('Nu am putut deschide chat-ul.');
    }
});