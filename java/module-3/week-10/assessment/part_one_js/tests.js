let should = chai.should();

describe('JS Assessment', () => {

	describe('filteredCount', () => {
		it('should handle an empty input array', () => {
				filteredCount('A', []).should.equal(0);
		}); 
		it('should handle an array with no matches', () => {
				filteredCount('A', [1, 'B', 'C']).should.equal(0);
		});
		it('should handle an array with exact matches', () => {
				filteredCount('A', ['A', 0, 'B', 'A', 'A']).should.equal(3);
		});
		it('should not count values that differ by case', () => {
				filteredCount('A', ['A', 0, 'B', 'a', 'A']).should.equal(2);
		});
		it('should not count values that are not strings', () => {
				filteredCount('1', ['a', '1', 'B', 1]).should.equal(1);
		});
  });

	describe('inProgressOrders', () => {
    const testArray = [
        { state: 'OH', status: 'In-Progress', weight: 14, cost: 14.25 },
        { state: 'OH', status: 'In-Progress', weight: 10, cost: 10.50 },
        { state: 'PA', status: 'Shipped', weight: 10, cost: 10.00 },
        { state: 'OH', status: 'Hold', weight: 12, cost: 20.99 },
        { state: 'PA', status: 'Delivered', weight: 18, cost: 18.75 },
        { state: 'PA', status: 'In-Progress', weight: 15, cost: 15.99 },
        { state: 'TX', status: 'Hold', weight: 12, cost: 20.99 },
        { state: 'OH', status: 'Shipped', weight: 12, cost: 12.50 },
    ];

    it('should handle an empty input array', () => {
        inProgressOrders('HI', []).should.equal(0);
    });
    it('should handle an array with no orders for that state', () => {
        inProgressOrders('HI', testArray).should.equal(0);
    });
    it('should handle an array with no in progress orders for that state', () => {
        inProgressOrders('TX', testArray).should.equal(0);
    });
    it('should handle an array with in progress orders for that state', () => {
        inProgressOrders('OH', testArray).should.equal(2);
				inProgressOrders('PA', testArray).should.equal(1);
    });
  }); 

  describe('orderVolume', () => {
    const testArray = [
        { state: 'OH', status: 'Delivered', weight: 14, cost: 14.25 },
        { state: 'OH', status: 'Delivered', weight: 10, cost: 10.50 },
        { state: 'PA', status: 'Shipped', weight: 10, cost: 10.00 },
        { state: 'OH', status: 'Hold', weight: 12, cost: 20.99 },
        { state: 'PA', status: 'Delivered', weight: 18, cost: 18.75 },
        { state: 'PA', status: 'In-Progress', weight: 15, cost: 15.99 },
        { state: 'TX', status: 'Hold', weight: 12, cost: 20.99 },
        { state: 'OH', status: 'Shipped', weight: 12, cost: 12.50 },
    ];

    it('should handle an empty input array', () => {
        const result = orderVolume('HI', []);
        result.should.have.property('count');
        result.count.should.equal(0);
        result.should.have.property('weight');
        result.weight.should.equal(0);
        result.should.have.property('sales');
        result.sales.should.equal(0);
    });
    it('should handle an array with no orders for that state', () => {
        const result = orderVolume('HI', testArray);
        result.should.have.property('count');
        result.count.should.equal(0);
        result.should.have.property('weight');
        result.weight.should.equal(0);
        result.should.have.property('sales');
        result.sales.should.equal(0);
    });
    it('should handle an array with no shipped or delivered orders for that state', () => {
        const result = orderVolume('TX', testArray);
        result.should.have.property('count');
        result.count.should.equal(0);
        result.should.have.property('weight');
        result.weight.should.equal(0);
        result.should.have.property('sales');
        result.sales.should.equal(0);
    });
    it('should handle an array with both shipped and delivered orders for that state', () => {
        const ohResult = orderVolume('OH', testArray);
        ohResult.should.have.property('count');
        ohResult.count.should.equal(3);
        ohResult.should.have.property('weight');
        ohResult.weight.should.equal(36);
        ohResult.should.have.property('sales');
        ohResult.sales.should.equal(37.25);

        const paResult = orderVolume('PA', testArray);
        paResult.should.have.property('count');
        paResult.count.should.equal(2);
        paResult.should.have.property('weight');
        paResult.weight.should.equal(28);
        paResult.should.have.property('sales');
        paResult.sales.should.equal(28.75);
    });
  }); 
});
