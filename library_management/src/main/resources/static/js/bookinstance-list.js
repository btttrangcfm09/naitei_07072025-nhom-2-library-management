document.addEventListener('DOMContentLoaded', function () {
    const el = document.getElementById("flashMessage");
    if (el) {
        setTimeout(() => {
        el.style.transition = "opacity 0.5s";
        el.style.opacity = 0;
        setTimeout(() => el.remove(), 500);
        }, 3000);
    }
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
    setupViewHandler(i18n);
    setupFilterLogic();
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
            const editUrl = `/admin/bookinstances/${bookInstance.id}/edit`;
            row.innerHTML = `
            <td class="col-sub-barcode">${bookInstance.barcode}</td>
            <td class="col-sub-callNumber">${bookInstance.callNumber}</td>
            <td class="col-sub-status">${formatStatus(bookInstance.status, i18n.status)}</td>
            <td class="col-sub-acquiredDate text-center">${bookInstance.acquiredDate}</td>
            <td class="col-sub-acquiredPrice text-center">${bookInstance.acquiredPrice || 'N/A'}</td>
            <td class="col-sub-actions">
                <div class="action-icons">
                   <a href="#" class="text-info js-view-bookinstance" 
                        data-bookinstance-id="${bookInstance.id}" 
                        title="${i18n.tooltip.view}">
                        <i class="mdi mdi-eye"></i>
                    </a>                   
                    <a href="${editUrl}" class="text-primary" title="${i18n.tooltip.edit}"><i class="mdi mdi-pencil"></i></a>
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

/**
 * Handles the view detail logic for BookInstances using a Bootstrap Modal,
 * following the specified logic pattern.
 * @param {object} i18n - The internationalization object.
 */
function setupViewHandler(i18n) {
    const modalElement = document.getElementById('bookinstanceDetailModal');

    if (!modalElement) {
        console.error('Modal with ID "bookinstanceDetailModal" not found in the DOM.');
        return;
    }

    const bookInstanceModal = new bootstrap.Modal(modalElement);

    document.addEventListener('click', function(event) {
        const viewButton = event.target.closest('.js-view-bookinstance');
        
        if (!viewButton) {
            return;
        }

        event.preventDefault(); 
        const id = viewButton.dataset.bookinstanceId;

        if (!id) {
            console.error('Could not get a valid data-bookinstance-id from the clicked button.');
            return;
        }

        fetch(`/admin/bookinstances/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                populateBookInstanceModal(data, i18n);
                bookInstanceModal.show();
            })
            .catch(error => {
                console.error('Error fetching bookinstance details:', error);
                Swal.fire({
                    title: i18n.deletebox.title,
                    text: i18n.infor.reload,
                    icon: 'error',
                    confirmButtonText: 'Đã hiểu'
                });
            });
    });
}

/**
 * Populates the book instance detail modal with data from the API.
 * @param {object} data - The book instance data object from the API.
 * @param {object} i18n - The internationalization object.
 */
function populateBookInstanceModal(data, i18n) {

    // Data for Edition
    setTextContentById('modal-edition-title', data.editionTitle || 'N/A');
    setTextContentById('modal-edition-isbn', data.isbn || 'N/A');
    
    const coverImage = document.getElementById('modal-edition-cover');
    const defaultCover = '/images/editions/default-cover.png'; // Đảm bảo đường dẫn này đúng
    coverImage.src = data.coverImageUrl || defaultCover;
    coverImage.onerror = function() {
        this.onerror = null;
        this.src = defaultCover;
    };

    //Data for Book
    setTextContentById('modal-book-title', data.bookTitle || 'N/A');

    // Data for BookInstance
    setTextContentById('modal-bookinstance-barcode', data.barcode || 'N/A');
    setTextContentById('modal-bookinstance-callnumber', data.callNumber || 'N/A');
    setTextContentById('modal-bookinstance-acquiredDate', data.acquiredDate || 'N/A');

    setTextContentById('modal-bookinstance-acquiredPrice',  data.acquiredPrice?.toString() || 'N/A');
    const statusText = formatStatus(data.status, i18n.status, false); 
    setTextContentById('modal-bookinstance-status', statusText);
    setTextContentById('modal-bookinstance-note', data.note ||  'Không có ghi chú.');
}

function setupFilterLogic() {

    const filterBySelect = document.getElementById('filterBy');
    const keywordInput = document.getElementById('keyword');

    const dateRangeFilter = document.getElementById('date-range-filter');
    const fromDateInput = document.getElementById('fromDate');
    const toDateInput = document.getElementById('toDate');

    const statusRangeFilter = document.getElementById('status-range-filter'); 
    const statusInput = document.getElementById('status');


    if (!filterBySelect || !dateRangeFilter || !statusRangeFilter) {
        console.error("Không tìm thấy các element cần thiết cho form filter. Kiểm tra lại ID trong HTML.");
        return;
    }

    function updateFormFields() {
        const selectedValue = filterBySelect.value;

        if (selectedValue === 'book_instance') {
            dateRangeFilter.style.display = 'flex';
            statusRangeFilter.style.display = 'flex';
            if (keywordInput) keywordInput.placeholder = 'Barcode hoặc Call Number...';
        } else { // 'edition'

            dateRangeFilter.style.cssText = "display: none !important;";
            statusRangeFilter.style.cssText = "display: none !important;";

            if (fromDateInput) {
                fromDateInput.value = '';
            }
            if (toDateInput) {
                toDateInput.value = '';
            }
            if (statusInput) {
                statusInput.value = ''; 
            }

            if (keywordInput) keywordInput.placeholder = 'Nhập tên sách, tên nhà xuất bản...';
        }
    }

    updateFormFields();
    filterBySelect.addEventListener('change', updateFormFields);
}
