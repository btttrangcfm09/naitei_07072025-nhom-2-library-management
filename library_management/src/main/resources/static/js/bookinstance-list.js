document.addEventListener('DOMContentLoaded', function () {
    const toggleButtons = document.querySelectorAll('.js-toggle-bookinstances');
    const bookInstancesTemplate = document.getElementById('bookinstances-table-template');

    const i18n = buildI18n(bookInstancesTemplate);

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

            fetch(`/admin/bookinstances/edition/${editionId}`)
                .then(response => response.json())
                .then(bookInstances => {
                    const newRow = document.createElement('tr');
                    newRow.id = `bookinstances-for-edition-${editionId}`;

                    const newCell = document.createElement('td');
                    newCell.colSpan = 7;
                    if (bookInstances.length > 0) {
                        newCell.classList.add('p-0');

                        // Create sub-table from template
                        newCell.appendChild(createBookInstancesTable(bookInstances, i18n));
                        newRow.appendChild(newCell);

                        editionRow.insertAdjacentElement('afterend', newRow);
                    }
                    else {
                        newCell.textContent = i18n.infor.nulldata;

                        newCell.classList.add('text-center', 'text-muted', 'p-3');

                        newRow.appendChild(newCell);
                        editionRow.insertAdjacentElement('afterend', newRow);
                    }
                })
                .catch(error => {
                    console.error('Error fetching bookinstances:', error);
                    alert(i18n.infor.reload);
                });

        });
    });

    setupDeleteHandler(i18n);

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
                    <a href="#" class="text-danger js-delete-bookinstance" 
                       data-bookinstance-id="${bookInstance.id}"
                       data-bookinstance-barcode="${bookInstance.barcode}" 
                       title="${i18n.tooltip.delete}">
                       <i class="mdi mdi-delete"></i>
                    </a>
                </div>
            </td>
        `;
            tableBody.appendChild(row);
        });
        return tableContainer;
    }
});

function setupDeleteHandler(i18n) {
    document.addEventListener('click', function (event) {
        const deleteButton = event.target.closest('.js-delete-bookinstance');

        if (!deleteButton) {
            return;
        }

        event.preventDefault();

        const id = deleteButton.dataset.bookinstanceId;
        const rowToDelete = deleteButton.closest('tr');
        const endpointUrl = '/admin/bookinstances';
        const barcode = deleteButton.dataset.bookinstanceBarcode;

        Swal.fire({
            title: i18n.deletebox.title,
            text: i18n.deletebox.text,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: i18n.deletebox.confirmbutton,
            cancelButtonText: i18n.deletebox.cancelbutton
        }).then((result) => {
            if (result.isConfirmed) {
                performGenericDelete(`${endpointUrl}/${id}`, rowToDelete, barcode, i18n);
            }
        });
    });
}
