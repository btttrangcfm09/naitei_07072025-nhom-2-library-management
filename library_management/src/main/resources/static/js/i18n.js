function buildI18n(template) {
    return {
        header: {
            barcode: template.dataset.headerBarcode,
            callNumber: template.dataset.headerCallnumber,
            status: template.dataset.headerStatus,
            acquiredDate: template.dataset.headerAcquireddate,
            acquiredPrice: template.dataset.headerAcquiredprice,
            actions: template.dataset.headerActions
        },
        tooltip: {
            view: template.dataset.tooltipView,
            edit: template.dataset.tooltipEdit,
            delete: template.dataset.tooltipDelete
        },
        status: {
            available: template.dataset.statusAvailable,
            repairing: template.dataset.statusRepairing,
            borrowed: template.dataset.statusBorrowed,
            lost: template.dataset.statusLost,
            damaged: template.dataset.statusDamaged,
            archived: template.dataset.statusArchived,
            reserved: template.dataset.statusReserved,
            unknown: template.dataset.statusUnknown
        },
        infor: {
            nulldata: template.dataset.inforNulldata,
            reload: template.dataset.inforReload
        },
        deletebox: {
            title: template.dataset.deleteboxTitle,
            text: template.dataset.deleteboxText,
            confirmbutton: template.dataset.deleteboxConfirmButton,
            cancelbutton: template.dataset.deleteboxCancelButton,
            successtitle: template.dataset.deleteboxSuccessTitle,
            errortitle: template.dataset.deleteboxErrorTitle

        }
    };
}

function buildI18nForEditions(template) {
    return {
        header: {
            title: template.dataset.headerTitle,
            isbn: template.dataset.headerIsbn,
            publisher: template.dataset.headerPublisher,
            year: template.dataset.headerYear,
            quantity: template.dataset.headerQuantity,
            actions: template.dataset.headerActions
        },
        tooltip: {
            view: template.dataset.tooltipView,
            edit: template.dataset.tooltipEdit,
            delete: template.dataset.tooltipDelete,
            disabled: template.dataset.tooltipDisabled
        },
        message: {
            confirmDelete: template.dataset.messageConfirmDelete
        },
        status: {
            cannotDelete: template.dataset.statusCannotDelete
        },
        infor: {
            reload: template.dataset.inforReload
        }
    };
}
