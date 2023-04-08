let should = chai.should();

describe('JS Practice Assessment', () => {

  describe('filteredSum', () => {
    it('should handle an empty array', () => {
        filteredSum([]).should.equal(0);
    });
    it('should sum values between 0 and 100', () => {
        filteredSum([0, 1, 14, 99]).should.equal(114);
    });
    it('should include 100 in sum', () => {
        filteredSum([100, 0, 100]).should.equal(200);
    });
    it('should ignore numbers less than 0', () => {
        filteredSum([-2, -300, -42]).should.equal(0);
    });
    it('should ignore numbers greater than 100', () => {
        filteredSum([150, 101, 300]).should.equal(0);
    });
    it('should sum only values between 0 and 100 (inclusive) ignoring other values', () => {
        filteredSum([0, 1, -2, 150, 14, 101, -402, 99, 100]).should.equal(214);
    });

  });

  describe('yearlyBookCount', () => {
    const testBookArray = [
        {
            title: 'Test Book 1',
            genres: ['Fiction', 'Fantasy', 'Science-Fiction'],
            pageCount: 200,
            datesRead: ['01-01-2010', '01-01-2011']
        },
        {
            title: 'Test Book 2',
            genres: ['Poetry'],
            pageCount: 100,
            datesRead: ['01-01-2010']
        },
        {
            title: 'Test Book 3',
            genres: ['Non-Fiction', 'History'],
            pageCount: 250,
            datesRead: ['01-01-2011']
        },
        {
            title: 'Test Book 4',
            genres: ['Non-Fiction', 'Biography'],
            pageCount: 350,
            datesRead: ['01-01-2012']
        },
        {
            title: 'Test Book 4',
            genres: ['Fiction', 'Mystery'],
            pageCount: 400,
            datesRead: ['01-01-2011']
        }
    ];

    it('should handle an empty input array', () => {
        const result = yearlyBookCount('2010', []);
        result.should.have.property('pagesFiction');
        result.pagesFiction.should.equal(0, 'pagesFiction');
        result.should.have.property('pagesNonFiction');
        result.pagesNonFiction.should.equal(0, 'pagesNonFiction');
    });
    it('should handle an empty list of datesRead for a book', () => {
        const result = yearlyBookCount('2010', [
            {
                title: 'Test Book',
                genres: ['Fiction', 'Mystery'],
                pageCount: 200,
                datesRead: []
            }
        ]);
        result.should.have.property('pagesFiction');
        result.pagesFiction.should.equal(0, 'pagesFiction');
        result.should.have.property('pagesNonFiction');
        result.pagesNonFiction.should.equal(0, 'pagesNonFiction');
    });    
    it('should handle an empty list of genres for a book, counting pages as non-fiction', () => {
        const result = yearlyBookCount('2010', [
            {
                title: 'Test Book',
                genres: [],
                pageCount: 200,
                datesRead: ['01-01-2010']
            }
        ]);
        result.should.have.property('pagesFiction');
        result.pagesFiction.should.equal(0, 'pagesFiction');
        result.should.have.property('pagesNonFiction');
        result.pagesNonFiction.should.equal(200, 'pagesNonFiction');
    });
    it('should handle an array with no books read for that year', () => {
        const result = yearlyBookCount('1999', testBookArray);
        result.should.have.property('pagesFiction');
        result.pagesFiction.should.equal(0, 'pagesFiction');
        result.should.have.property('pagesNonFiction');
        result.pagesNonFiction.should.equal(0, 'pagesNonFiction');
    });
    it('should correctly count fiction and non-fiction pages for books read that year', () => {
        const result2010 = yearlyBookCount('2010', testBookArray);
        result2010.should.have.property('pagesFiction');
        result2010.pagesFiction.should.equal(200, 'pagesFiction');
        result2010.should.have.property('pagesNonFiction');
        result2010.pagesNonFiction.should.equal(100, 'pagesNonFiction');

        const result2011 = yearlyBookCount('2011', testBookArray);
        result2011.should.have.property('pagesFiction');
        result2011.pagesFiction.should.equal(600, 'pagesFiction');
        result2011.should.have.property('pagesNonFiction');
        result2011.pagesNonFiction.should.equal(250, 'pagesNonFiction');

        const result2012 = yearlyBookCount('2012', testBookArray);
        result2012.should.have.property('pagesFiction');
        result2012.pagesFiction.should.equal(0, 'pagesFiction');
        result2012.should.have.property('pagesNonFiction');
        result2012.pagesNonFiction.should.equal(350, 'pagesNonFiction');
    });
  }); 

  
  describe('OPTIONAL - yearlyBookStatistics', () => {
    const testBookArray = [
        {
            title: 'Test Book 1',
            genres: ['Fiction', 'Fantasy', 'Science-Fiction'],
            pageCount: 200,
            datesRead: ['01-01-2010', '01-01-2011']
        },
        {
            title: 'Test Book 2',
            genres: ['Poetry'],
            pageCount: 100,
            datesRead: ['01-01-2010']
        },
        {
            title: 'Test Book 3',
            genres: ['Non-Fiction', 'History'],
            pageCount: 250,
            datesRead: ['01-01-2011']
        },
        {
            title: 'Test Book 4',
            genres: ['Non-Fiction', 'Biography'],
            pageCount: 350,
            datesRead: ['01-01-2012']
        },
        {
            title: 'Test Book 4',
            genres: ['Fiction', 'Fantasy'],
            pageCount: 400,
            datesRead: ['01-01-2011']
        }
    ];

    it('should handle an empty input array', () => {
        const result = yearlyBookStatistics('2010', []);
        result.should.have.property('totalBooks');
        result.totalBooks.should.equal(0, 'totalBooks');
    });
    it('should handle an empty list of datesRead for a book', () => {
        const result = yearlyBookStatistics('2010', [
            {
                title: 'Test Book',
                genres: ['Fiction', 'Mystery'],
                pageCount: 200,
                datesRead: []
            }
        ]);
        result.should.have.property('totalBooks');
        result.totalBooks.should.equal(0, 'totalBooks');
    });    
    it('should handle an empty list of genres for a book, counting only in totalBooks', () => {
        const result = yearlyBookStatistics('2010', [
            {
                title: 'Test Book',
                genres: [],
                pageCount: 200,
                datesRead: ['01-01-2010']
            }
        ]);
        result.should.have.property('totalBooks');
        result.totalBooks.should.equal(1, 'totalBooks');
    });
    it('should handle an array with no books read for that year', () => {
        const result = yearlyBookStatistics('1999', testBookArray);
        result.should.have.property('totalBooks');
        result.totalBooks.should.equal(0, 'totalBooks');
    });
    it('should correctly count totalBooks with genre counts for books read that year', () => {
        const result2010 = yearlyBookStatistics('2010', testBookArray);
        result2010.should.have.property('totalBooks');
        result2010.totalBooks.should.equal(2, 'For 2010 totalBooks');
        result2010.should.have.property('Fiction');
        result2010['Fiction'].should.equal(1, 'For 2010 Fiction count');
        result2010.should.have.property('Science-Fiction');
        result2010['Science-Fiction'].should.equal(1, 'For 2010 Science-Fiction count');
        result2010.should.have.property('Fantasy');
        result2010['Fantasy'].should.equal(1, 'For 2010 Fantasy count');
        result2010.should.have.property('Poetry');
        result2010['Poetry'].should.equal(1, 'For 2010 Poetry count');

        const result2011 = yearlyBookStatistics('2011', testBookArray);
        result2011.should.have.property('totalBooks');
        result2011.totalBooks.should.equal(3, 'For 2011 totalBooks');
        result2011.should.have.property('Fiction');
        result2011['Fiction'].should.equal(2, 'For 2011 Fiction count');
        result2011.should.have.property('Science-Fiction');
        result2011['Science-Fiction'].should.equal(1, 'For 2011 Science-Fiction count');
        result2011.should.have.property('Fantasy');
        result2011['Fantasy'].should.equal(2, 'For 2011 Fantasy count');

        const result2012 = yearlyBookStatistics('2012', testBookArray);
        result2012.should.have.property('totalBooks');
        result2012.totalBooks.should.equal(1, 'For 2012 totalBooks');
        result2012.should.have.property('Non-Fiction');
        result2012['Non-Fiction'].should.equal(1, 'For 2012 Non-Fiction count');
        result2012.should.have.property('Biography');
        result2012['Biography'].should.equal(1, 'For 2011 Biography count');
    });
  }); 
});
