document.addEventListener('DOMContentLoaded', function () {
    const toggleButtons = document.querySelectorAll('.js-toggle-bookinstances');
    const bookInstancesTemplate = document.getElementById('bookinstances-table-template');

    // Get translated strings from template
    const i18n = {
        header: {
            barcode: bookInstancesTemplate.dataset.headerBarcode,
            callNumber: bookInstancesTemplate.dataset.headerCallnumber,
            status: bookInstancesTemplate.dataset.headerStatus,
            acquiredDate: bookInstancesTemplate.dataset.headerAcquireddate,
            acquiredPrice: bookInstancesTemplate.dataset.headerAcquiredprice,
            actions: bookInstancesTemplate.dataset.headerActions
        },
        tooltip: {
            view: bookInstancesTemplate.dataset.tooltipView,
            edit: bookInstancesTemplate.dataset.tooltipEdit,
            delete: bookInstancesTemplate.dataset.tooltipDelete
        },
        status: {
            available: bookInstancesTemplate.dataset.statusAvailable,
            repairing: bookInstancesTemplate.dataset.statusRepairing,
            borrowed: bookInstancesTemplate.dataset.statusBorrowed,
            lost: bookInstancesTemplate.dataset.statusLost,
            damaged: bookInstancesTemplate.dataset.statusDamaged,
            archived: bookInstancesTemplate.dataset.statusArchived,
            reserved: bookInstancesTemplate.dataset.statusReserved,
            unknown: bookInstancesTemplate.dataset.statusUnknown
        }
    };

    toggleButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            const editionId = this.dataset.editionId;
            const editionRow = document.getElementById(`edition-row-${editionId}`);
            const existingBookInstancesRow = document.getElementById(`bookinstances-for-edition-${editionId}`);

            // if already exists -> remove it and return
            if (existingBookInstancesRow) {
                existingBookInstancesRow.remove();
                return;
            }

            fetch(`/admin/bookinstances/${editionId}/bookinstances`)
                .then(response => response.json())
                .then(bookInstances => {
                    if (bookInstances.length > 0) {
                        const newRow = document.createElement('tr');
                        newRow.id = `bookinstances-for-edition-${editionId}`;
                        const newCell = document.createElement('td');
                        newCell.colSpan = 7;
                        newCell.classList.add('p-0');
                        
                        // Create sub-table from template
                        newCell.appendChild(createBookInstancesTable(bookInstances, i18n));
                        newRow.appendChild(newCell);

                        editionRow.insertAdjacentElement('afterend', newRow);
                    }
                })
                .catch(error => console.error('Error fetching bookinstances:', error));
        });
    });

    function createBookInstancesTable(bookInstances, i18n) {
        // Clone template's content
        const templateContent = bookInstancesTemplate.content.cloneNode(true);
        const tableContainer = templateContent.querySelector('.bookinstances-sub-table');
        const tableHeader = tableContainer.querySelector('thead tr');
        const tableBody = tableContainer.querySelector('tbody');

        // Fill in the translated column headers
        tableHeader.querySelector('.col-sub-barcode').textContent = i18n.header.barcode;
        tableHeader.querySelector('.col-sub-callNumber').textContent = i18n.header.callNumber;
        tableHeader.querySelector('.col-sub-status').textContent = i18n.header.status;
        tableHeader.querySelector('.col-sub-acquiredDate').textContent = i18n.header.acquiredDate;
        tableHeader.querySelector('.col-sub-acquiredPrice').textContent = i18n.header.acquiredPrice;
        tableHeader.querySelector('.col-sub-actions').textContent = i18n.header.actions;

        // Create data rows
        bookInstances.forEach(bookInstance => {
            const row = document.createElement('tr');
        row.innerHTML = `
            <td class="col-sub-barcode">${bookInstance.barcode}</td>
            <td class="col-sub-callNumber">${bookInstance.callNumber}</td>
            <td class="col-sub-status">${formatStatus(bookInstance.status, i18n.status)}</td>
            <td class="col-sub-acquiredDate text-center">${bookInstance.acquiredDate}</td>
            <td class="col-sub-acquiredPrice text-center">${bookInstance.acquiredPrice || 'N/A'}</td>
            <td class="col-sub-actions">
                <div class="action-icons">
                    <a href="#" class="text-info" title="${i18n.tooltip.view}"><i class="mdi mdi-eye"></i></a>
                    <a href="#" class="text-primary" title="${i18n.tooltip.edit}"><i class="mdi mdi-pencil"></i></a>
                    <a href="#" class="text-danger" title="${i18n.tooltip.delete}"><i class="mdi mdi-delete"></i></a>
                </div>
            </td>
        `;
            tableBody.appendChild(row);
        });
        return tableContainer;
    }
});
