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

function performGenericDelete(fullUrl, rowElement, barcode,  translations) {
    // tokenMeta and headerMeta wil be used for spring security with login log out, but now it isn't implement.
    // const tokenMeta = document.querySelector('meta[name="_csrf"]');
    // const headerMeta = document.querySelector('meta[name="_csrf_header"]');
    const headers = {};
    // if (tokenMeta && headerMeta) {
    //     headers[headerMeta.content] = tokenMeta.content;
    // }
    fetch(fullUrl, {
        method: 'DELETE',
        headers: headers
    })
    .then(response => {
        return response.json().then(data => {
            if(!response.ok){
                const errorMessage = data.error || `Không thể xóa ${barcode}. (Mã lỗi: ${response.status})`;
                throw new Error(errorMessage);
            }
            return data;
        });
    })
    .then(data => {
        Swal.fire(
            translations.deletebox.successtitle,
            data.message,
            'success'
        );
        if (rowElement) {
            rowElement.remove();
        }
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire(
            translations.deletebox.errortitle,
            error.message,
            'error'
        );
    });
}
