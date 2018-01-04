
$(document).ready(function () {
        	
	$('#jqgrid-parent-div').css("margin-left","1px");
			
			/**
			 *  Add datepicker calender to textbox.
			 */
        	$('#extendedDate').datepicker();
        	
        	       
        	/**
        	 *  Get all contract records from system.
        	 */
            $("#jqGrid").jqGrid({
            	url: "contractData.json",
            	accepts: "application/json; charset=utf-8",
            	contentType: "application/json; charset=utf-8",
                datatype: "json",
    			colModel: [
                    { label: 'ContractId', name: 'contractId', key: true, width: 100 }, 
                    { label:'Valid To', name: 'validUpto', width: 100 ,sortable: false,formatter: dateFormatter},
                    { label:'Extended Date', name: 'extendedUpto',sortable: false, width: 120,formatter: dateFormatter },
                    { label:'D.Left', name: 'daysLeft', width: 50,sortable: true, formatter: daysLeftFromValidity },
                    { label:'', name: '', width: 120,formatter: extensionMailLink},
                    { label:'', name: '', width: 120,formatter: encashmentMailLink},
                    { label: 'Contractor Name', name: 'contractor.contractorName', width: 200 },
                    { label: 'Bank Name', name: 'bank.bankName', width: 100 ,sortable: false},
                    { label: 'IFSC Code', name: 'bank.ifscNumber', width: 100, sortable: false },
                    { label:'Guarantee', name: 'gaurateeAmount', width: 75,sortable: false },
                    { label:'Valid From', name: 'validFrom', width: 100 ,sortable: false, formatter: dateFormatter}
                  
                ],
                                             
				viewrecords: true,
                width: 985,
                height: 250,
                rowNum: 20,
                shrinkToFit:false, 
                forceFit:true,
                loadonce: true,
                pager: "#jqGridPager",
           });


        /**
         * Format date from millisecond to Dateformat: dd/MM/yyyy.
         */ 
        function dateFormatter(cellValue,options,rowObject){
        	//alert(options.colModel.name);
           	var date = $.format.date(cellValue,'MM/dd/yyyy');
           	if(date == null){
           		date = '';
           	}
           	return (date);
        }
        
        /**
         *  Calculate number of days left b/w current date and Valid Upto Date.
         */
        function daysLeftFromValidity(cellValue,options,rowObject){
        	
        	var extendedDateInMilliSeconds = rowObject.extendedUpto;
        	var validUptoDateInMilliSeconds = rowObject.validUpto;
        	var currentDateInMilliSeconds = (new Date()).getTime();
        	
        	var daysLeftForValidityInSeconds = 0;
        	if(extendedDateInMilliSeconds != null){
        		daysLeftForValidityInSeconds = extendedDateInMilliSeconds - currentDateInMilliSeconds;
        	}else{
        		daysLeftForValidityInSeconds = validUptoDateInMilliSeconds - currentDateInMilliSeconds;
        	}
        	
        	var daysLeft = parseInt(daysLeftForValidityInSeconds/(24*3600*1000));
        	
        	if(daysLeft >0){
        		return daysLeft;
        	}else
        		return '';
        }
        
        
        /**
         *  Get ContractId based on entered text(ajax call at 3rd text).
         */
        $( "#contractId" ).autocomplete({
				source: function( request, response ) {
							$.ajax({
								url: "/BGMS/user/getContractListBasedOnSearch.json",
								dataType: "json",
								contentType: "application/json; charset=utf-8",
								data: {
										contractId: request.term
									},
								success: function( data ) {
									response( data );
								}
							});
						},
				minLength: 3,
				open: function() {
						$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
						},
				close: function() {
						$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
						}
				});
        
        /**
         *  Set encashmentMail link based on number of days left from expiry.
         */
        function encashmentMailLink(cellValue,options,rowObject){
        	var extendedDateInMilliSeconds = rowObject.extendedUpto;
        	var validUptoDateInMilliSeconds = rowObject.validUpto;
        	var currentDateInMilliSeconds = (new Date()).getTime();
        	var encashmentNoticeDateInMilliSeconds = rowObject.encashmentNoticeDate;
        	
        	var daysLeftForValidityInSeconds = 0;
        	if(extendedDateInMilliSeconds != null){
        		daysLeftForValidityInSeconds = extendedDateInMilliSeconds - currentDateInMilliSeconds;
        	}else{
        		daysLeftForValidityInSeconds = validUptoDateInMilliSeconds - currentDateInMilliSeconds;
        	}
        	
        	var daysLeft = parseInt(daysLeftForValidityInSeconds/(24*3600*1000));
        	if(0<daysLeft && daysLeft<=15){
        		
        		if((encashmentNoticeDateInMilliSeconds == null) || (encashmentNoticeDateInMilliSeconds<(extendedDateInMilliSeconds-(15*24*3600*1000)))){
        			return '<a class="a-hyperlink" href="sendEncashmentMail/'+rowObject.contractId+'">SendEncashmentNotice</a>';
        		}else
            		return '';
           	}else
        		return '';
        }
        
        /**
         * Set extensionMail link based on number of days left from expiry.
         */
        function extensionMailLink(cellValue,options,rowObject){
        	var extendedDateInMilliSeconds = rowObject.extendedUpto;
        	var validUptoDateInMilliSeconds = rowObject.validUpto;
        	var currentDateInMilliSeconds = (new Date()).getTime();
        	var extensionNoticeDateInMilliSeconds = rowObject.extensionNoticeDate;
        	
        	
        	var daysLeftForValidityInSeconds = 0;
        	if(extendedDateInMilliSeconds != null){
        		daysLeftForValidityInSeconds = extendedDateInMilliSeconds - currentDateInMilliSeconds;
        	}else{
        		daysLeftForValidityInSeconds = validUptoDateInMilliSeconds - currentDateInMilliSeconds;
        	}
        	
        	var daysLeft = parseInt(daysLeftForValidityInSeconds/(24*3600*1000));
        	
        	if(0<daysLeft && daysLeft<=30){
        		alert(daysLeft);
        		if((extensionNoticeDateInMilliSeconds == null) || (extensionNoticeDateInMilliSeconds<(extendedDateInMilliSeconds-(15*24*3600*1000)))){
        			alert(rowObject.contractId);
        			return '<a class="a-hyperlink" href="sendExtensionNotice/'+rowObject.contractId+'">SendExtensionNotice</a>';
        		}else{
        			return '';
        		}
           	}else{
        		return '';
           	}
        }
			


});	