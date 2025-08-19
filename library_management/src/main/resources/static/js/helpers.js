function formatStatus(status, translations) {
    if (!status) {
        return '<span class="badge badge-secondary">${translations.unknown}</span>';
    }

    const statusMap = {
        'AVAILABLE': { 
            badgeClass: 'badge-success', 
            text: translations.available 
        },
        'REPAIRING': { 
            badgeClass: 'badge-warning', 
            text: translations.repairing 
        },
        'BORROWED': { 
            badgeClass: 'badge-info', 
            text: translations.borrowed 
        },
        'LOST': { 
            badgeClass: 'badge-danger', 
            text: translations.lost 
        },
        'RESERVED': { 
            badgeClass: 'badge-info', 
            text: translations.reserved 
        },
        'DAMAGED': { 
            badgeClass: 'badge-danger', 
            text: translations.damaged 
        },
        'ARCHIVED': { 
            badgeClass: 'badge-secondary', 
            text: translations.archived 
        }
    };
    const defaultStatus = { 
        badgeClass: 'badge-secondary', 
        text: status 
    };    
    const upperCaseStatus = status.toUpperCase();
    const statusInfo = statusMap[upperCaseStatus] || defaultStatus;
    return `<span class="badge ${statusInfo.badgeClass}">${statusInfo.text}</span>`;
}
