const commonUtil =
    {
    alert: function(msg, type, doc)
    {
        if(typeof(type) =="undefined")
        {
            type = "success";
        }

        var divElement = $("<div></div>").addClass('alert').addClass('alert-'+type).addClass('alert-dismissible');
        divElement.css(
        {
            "position": "absolute",
            "top": "80px",
            "left": "50%",
            "transform":"-40px 0 0 0",
        });
        divElement.text(msg);
        var closeBtn = $('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
        $(divElement).append(closeBtn);
        doc.getElementById("body").append(divElement);
        return divElement;
    },
    message: function(msg, type, doc)
    {
        var divElement = commonUtil.alert(msg, type, doc);
        var isIn = false;
        divElement.on(
            {
            mouseover : function(){isIn = true;},
            mouseout  : function(){isIn = false;}
        });
        setTimeout(function() {
            var IntervalMS = 20;
            var floatSpace = 60;
            var nowTop = divElement.offset().top;
            var stopTop = nowTop - floatSpace;
            divElement.fadeOut(IntervalMS * floatSpace);
            var upFloat = setInterval(function()
            {
                if (nowTop >= stopTop)
                {
                    divElement.css({"top": nowTop--});
                } else {
                    clearInterval(upFloat);
                    divElement.remove();
                }
            }, IntervalMS);
            if (isIn)
            {
                clearInterval(upFloat);
                divElement.stop();
            }
            divElement.hover(function()
            {
                clearInterval(upFloat);
                divElement.stop();
            },function()
            {
                divElement.fadeOut(IntervalMS * (nowTop - stopTop));
                upFloat = setInterval(function()
                {
                    if (nowTop >= stopTop)
                    {
                        divElement.css({"top": nowTop--});
                    }
                    else
                    {
                        clearInterval(upFloat);
                        divElement.remove();
                    }
                }, IntervalMS);
            });
        }, 1500);
    }
};