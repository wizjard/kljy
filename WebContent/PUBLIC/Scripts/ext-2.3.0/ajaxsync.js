Ext.lib.Ajax.request = function(method, uri, cb, data, options) {
    if(options){
        var hs = options.headers;
        if(hs){
            for(var h in hs){
                if(hs.hasOwnProperty(h)){
                    this.initHeader(h, hs[h], false);
                }
            }
        }
        if(options.xmlData){
            if (!hs || !hs['Content-Type']){
                this.initHeader('Content-Type', 'text/xml', false);
            }
            method = (method ? method : (options.method ? options.method : 'POST'));
            data = options.xmlData;
        }else if(options.jsonData){
            if (!hs || !hs['Content-Type']){
                this.initHeader('Content-Type', 'application/json', false);
            }
            method = (method ? method : (options.method ? options.method : 'POST'));
            data = typeof options.jsonData == 'object' ? Ext.encode(options.jsonData) : options.jsonData;
        }
    }

   return this["sync" in options ? "syncRequest" : "asyncRequest"](method, uri, cb, data);
}
Ext.lib.Ajax.syncRequest = function(method, uri, callback, postData)
{
    var o = this.getConnectionObject();

    if (!o) {
        return null;
    }
    else {
        o.conn.open(method, uri, false); 

        if (this.useDefaultXhrHeader) {
            if (!this.defaultHeaders['X-Requested-With']) {
                this.initHeader('X-Requested-With', this.defaultXhrHeader, true);
            }
        }

        if(postData && this.useDefaultHeader && (!this.hasHeaders || !this.headers['Content-Type'])){
            this.initHeader('Content-Type', this.defaultPostHeader);
        }

        if (this.hasDefaultHeaders || this.hasHeaders) {
            this.setHeader(o);
        }

        o.conn.send(postData || null);
        this.handleTransactionResponse(o, callback);
        return o;
    }
}
