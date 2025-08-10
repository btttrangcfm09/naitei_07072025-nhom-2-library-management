(function($) {
  'use strict';
  $(function() {
    var sidebar = $('.sidebar');

    //Add active class to nav-link based on url dynamically
    //Active class can be hard coded directly in html file also as required
    var current = location.pathname.split("/").slice(-1)[0].replace(/^\/|\/$/g, '');
    $('.nav li a', sidebar).each(function() {
      var $this = $(this);
      if (current === "") {
        //for root url
        if ($this.attr('href').indexOf("dashboard") !== -1) {
          $(this).parents('.nav-item').last().addClass('active');
          if ($(this).parents('.sub-menu').length) {
            $(this).closest('.collapse').addClass('show');
            $(this).addClass('active');
          }
        }
      } else {
        //for other url
        if ($this.attr('href').indexOf(current) !== -1) {
          $(this).parents('.nav-item').last().addClass('active');
          if ($(this).parents('.sub-menu').length) {
            $(this).closest('.collapse').addClass('show');
            $(this).addClass('active');
          }
        }
      }
    })

    //Close other submenu in sidebar on opening any
    sidebar.on('show.bs.collapse', '.collapse', function() {
      sidebar.find('.collapse.show').collapse('hide');
    });

    //Change sidebar and content-wrapper height
    applyStyles();

    function applyStyles() {
      //Applying perfect scrollbar
      if ($('.scroll-container').length) {
        const ScrollContainer = new PerfectScrollbar('.scroll-container');
      }
    }

    //checkbox and radios
    $(".form-check label,.form-radio label").append('<i class="input-helper"></i>');

    // Library specific functionality

    // Notification dropdown functionality
    $('.count-indicator').on('click', function(e) {
      e.preventDefault();
      var $dropdown = $(this).next('.dropdown-menu');
      $dropdown.toggleClass('show');
    });

    // Close notification dropdown when clicking outside
    $(document).on('click', function(e) {
      if (!$(e.target).closest('.nav-item.dropdown').length) {
        $('.dropdown-menu').removeClass('show');
      }
    });

    // Mark notification as read
    $('.dropdown-menu .dropdown-item').on('click', function(e) {
      e.preventDefault();
      $(this).addClass('read');
      // Update notification count
      var count = parseInt($('.count').text()) - 1;
      if (count <= 0) {
        $('.count').hide();
      } else {
        $('.count').text(count);
      }
    });

    // Quick actions for library management

    // Quick book add button
    $('.btn:contains("Add New Book")').on('click', function(e) {
      e.preventDefault();
      // Redirect to add book page or show modal
      console.log('Redirecting to add new book...');
      // window.location.href = '/admin/books/add';
    });

    // Quick user add button
    $('.btn:contains("Add New User")').on('click', function(e) {
      e.preventDefault();
      // Redirect to add user page or show modal
      console.log('Redirecting to add new user...');
      // window.location.href = '/admin/users/add';
    });

    // Issue book button
    $('.btn:contains("Issue Book")').on('click', function(e) {
      e.preventDefault();
      // Show issue book modal or redirect
      console.log('Opening issue book dialog...');
    });

    // Book status toggle
    $('.badge').on('click', function() {
      var $this = $(this);
      if ($this.hasClass('badge-success') && $this.text() === 'Available') {
        // Quick actions for available books
        console.log('Book is available for lending');
      } else if ($this.hasClass('badge-danger') && $this.text() === 'Overdue') {
        // Quick actions for overdue books
        console.log('Send overdue notice');
      }
    });

    // User status management
    $('td:contains("Active"), td:contains("Inactive")').on('click', function() {
      var status = $(this).text();
      var userName = $(this).siblings().eq(1).text();
      console.log('Manage user status for: ' + userName + ' - Current: ' + status);
    });

    // Table row hover effects
    $('.table tbody tr').hover(
      function() {
        $(this).addClass('table-hover-effect');
      },
      function() {
        $(this).removeClass('table-hover-effect');
      }
    );

    // Auto-refresh notifications every 30 seconds
    setInterval(function() {
      // Here you would make an AJAX call to check for new notifications
      console.log('Checking for new notifications...');
      // updateNotifications();
    }, 30000);

    // Library-specific form validations
    function validateLibraryForms() {
      // Book form validation
      $('#bookForm').on('submit', function(e) {
        var title = $('#bookTitle').val();
        var author = $('#bookAuthor').val();
        var isbn = $('#bookISBN').val();

        if (!title || !author || !isbn) {
          e.preventDefault();
          alert('Please fill in all required fields for the book.');
        }
      });

      // User form validation
      $('#userForm').on('submit', function(e) {
        var email = $('#userEmail').val();
        var name = $('#userName').val();

        if (!email || !name) {
          e.preventDefault();
          alert('Please fill in all required fields for the user.');
        }
      });

      // Borrow request validation
      $('#borrowForm').on('submit', function(e) {
        var userId = $('#userId').val();
        var bookId = $('#bookId').val();
        var borrowDate = $('#borrowDate').val();
        var returnDate = $('#returnDate').val();

        if (!userId || !bookId || !borrowDate || !returnDate) {
          e.preventDefault();
          alert('Please fill in all required fields for the borrow request.');
        }

        // Validate return date is after borrow date
        if (new Date(returnDate) <= new Date(borrowDate)) {
          e.preventDefault();
          alert('Return date must be after borrow date.');
        }
      });
    }

    validateLibraryForms();

    // Quick stats update
    function updateQuickStats() {
      // Simulate real-time stats update
      $('.card-statistics').each(function() {
        var $card = $(this);
        var $number = $card.find('h3');
        var currentValue = parseInt($number.text().replace(/,/g, ''));

        // Random small increment for demo
        var increment = Math.floor(Math.random() * 3);
        if (increment > 0) {
          $number.text((currentValue + increment).toLocaleString());
        }
      });
    }

    // Update stats every 2 minutes
    setInterval(updateQuickStats, 120000);
  });
})(jQuery);
