/**
 * User: tango
 * Date: 13-7-1
 * Time: 下午7:08
 */
Array.prototype.filter = function (prop) {
    var filterArray = [];
    for (var i = 0; i < this.length; i++) {
        if (this[i][prop]) {
            filterArray.push(this[i][prop]);
        }
    }
    return filterArray;
};

Array.prototype.indexOf = function (value) {
    for (var i = 0; i < this.length; i++) {
        if (value == this[i]) {
            return i;
        }
    }
    return -1;
};

if (!"".trim) {
    String.prototype.trim = function (isGlobal) {
        if (isGlobal) {
            return this.replace(/\s/g, '');
        }
        return this.replace(/^\s*|\s*$/, '');
    }
}