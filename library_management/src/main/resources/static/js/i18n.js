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
        }
    };
}
