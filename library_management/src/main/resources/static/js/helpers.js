function formatStatus(status, translations) {
    if (!status) {
        return '<span class="badge badge-secondary">${translations.unknown}</span>';
    }

    let badgeClass = '';
    let translatedStatus = ''; 

    switch (status.toUpperCase()) {
        case 'AVAILABLE':
            badgeClass = 'badge-success';
            translatedStatus = translations.available;
            break;
        case 'REPAIRING':
            badgeClass = 'badge-warning';
            translatedStatus = translations.repairing;
            break;
        case 'BORROWED':
            badgeClass = 'badge-info';
            translatedStatus = translations.borrowed;
            break;
        case 'LOST':
            badgeClass = 'badge-danger';
            translatedStatus = translations.lost;
            break;
        case 'RESERVED':
            badgeClass = 'badge-info';
            translatedStatus = translations.reserved;
        case 'DAMAGED':
            badgeClass = 'badge-danger';
            translatedStatus = translations.damaged;
        case 'ARCHIVED':
            badgeClass = 'badge-secondary';
            translatedStatus = translations.archived;
        default:
            badgeClass = 'badge-secondary';
            translatedStatus = status;
            break;
    }

    return `<span class="badge ${badgeClass}">${translatedStatus}</span>`;
}
