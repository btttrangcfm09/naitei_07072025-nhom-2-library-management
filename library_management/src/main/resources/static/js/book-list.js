document.addEventListener('DOMContentLoaded', function () {
    const toggleButtons = document.querySelectorAll('.js-toggle-editions');
    const editionsTemplate = document.getElementById('editions-table-template');

    // Get translated strings from template
    const i18n = {
        header: {
            title: editionsTemplate.dataset.headerTitle,
            isbn: editionsTemplate.dataset.headerIsbn,
            publisher: editionsTemplate.dataset.headerPublisher,
            year: editionsTemplate.dataset.headerYear,
            quantity: editionsTemplate.dataset.headerQuantity,
            actions: editionsTemplate.dataset.headerActions
        },
        tooltip: {
            view: editionsTemplate.dataset.tooltipView,
            edit: editionsTemplate.dataset.tooltipEdit,
            delete: editionsTemplate.dataset.tooltipDelete
        }
    };

    toggleButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            const bookId = this.dataset.bookId;
            const bookRow = document.getElementById(`book-row-${bookId}`);
            const existingEditionsRow = document.getElementById(`editions-for-book-${bookId}`);

            // if already exists -> remove it and return
            if (existingEditionsRow) {
                existingEditionsRow.remove();
                return;
            }

            fetch(`/admin/books/${bookId}/editions`)
                .then(response => response.json())
                .then(editions => {
                    if (editions.length > 0) {
                        const newRow = document.createElement('tr');
                        newRow.id = `editions-for-book-${bookId}`;
                        const newCell = document.createElement('td');
                        newCell.colSpan = 6; // Ensure correct column count
                        newCell.classList.add('p-0');

                        // Create sub-table from template
                        newCell.appendChild(createEditionsTable(editions, i18n));
                        newRow.appendChild(newCell);
                        
                        bookRow.insertAdjacentElement('afterend', newRow);
                    }
                })
                .catch(error => console.error('Error fetching editions:', error));
        });
    });

    function createEditionsTable(editions, i18n) {
        // Clone template's content
        const templateContent = editionsTemplate.content.cloneNode(true);
        const tableContainer = templateContent.querySelector('.editions-sub-table');
        const tableHeader = tableContainer.querySelector('thead tr');
        const tableBody = tableContainer.querySelector('tbody');

        // Fill in the translated column headers
        tableHeader.querySelector('.col-sub-title').textContent = i18n.header.title;
        tableHeader.querySelector('.col-sub-isbn').textContent = i18n.header.isbn;
        tableHeader.querySelector('.col-sub-publisher').textContent = i18n.header.publisher;
        tableHeader.querySelector('.col-sub-year').textContent = i18n.header.year;
        tableHeader.querySelector('.col-sub-quantity').textContent = i18n.header.quantity;
        tableHeader.querySelector('.col-sub-actions').textContent = i18n.header.actions;

        // Create data rows
        editions.forEach(edition => {
            const row = document.createElement('tr');
        row.innerHTML = `
            <td class="col-sub-title">${edition.title}</td>
            <td class="col-sub-isbn">${edition.isbn}</td>
            <td class="col-sub-publisher">${edition.publisherName}</td>
            <td class="col-sub-year text-center">${edition.publicationYear || 'N/A'}</td>
            <td class="col-sub-quantity text-center">${edition.availableQuantity}</td>
            <td class="col-sub-actions">
                <!-- BỌC CÁC ICON VÀO MỘT DIV CONTAINER -->
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
