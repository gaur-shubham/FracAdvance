

function geninput(pid) {
	outputcr();
	$("#run1").removeClass("btn-warning");
	$("#run1").addClass("btn-danger");
	$("#run1").attr("disabled", "disabled");
	$("#run1").val("Please do not refresh the page");
	$("#show1").removeClass("btn-primary");
	$("#show1").addClass("btn-danger");
	$("#show1").attr("disabled", "disabled");
	$("#show1").val("Please do not refresh the page");
	var URL = "/simulate";
	$.ajax({
				url : URL,
				data : {
					pId : pid
				},
				success : function(suc) {
					if (suc == "success2") {
						genoutput(pid);
					} else {
						alert("You had left some inputs . Please check and fill them.");
						location.reload(true);
					}
				}
			});
};
function genoutput(pid) {
	finalcr();
	var URL = "/simulate";
	$.ajax({
				url : URL,
				data : {
					pId : pid
				},
				success : function(suc1) {
					if (suc1 == "success3") {
						savedata(pid);
					} else {
						alert("You had filled some wrong inputs . Please check and correct them.");
						location.reload(true);
					}
				}
			});
};
function savedata(pid) {
	var URL = "/simulate";
	$.ajax({
		url : URL,
		data : {
			pId : pid
		},
		success : function(msg) {
			if (msg == "success") {
				lastfinalcr(pid);
				$(".onetmenu").css('background', 'none');
				$(".onetmenu").css('color', 'black');
				relocatepg(pid);
			} else {
				location.reload(true);
			}
		}
	});
};

var current_progress = 0;
function inputcr() {
	var interval = setInterval(function() {
		if (current_progress < 20) {
			current_progress += 1;
		}
		$("#myBar").css("width", current_progress + "%").attr("aria-valuenow",
				current_progress).text(current_progress + "% Complete");
		if (current_progress >= 100)
			clearInterval(interval);
	}, 100);
};
function outputcr() {
	current_progress = 20;
	var interval = setInterval(function() {
		if (current_progress < 70) {
			current_progress += 1;
		}
		$("#myBar").css("width", current_progress + "%").attr("aria-valuenow",
				current_progress).text(current_progress + "% Complete");
		if (current_progress >= 100)
			clearInterval(interval);
	}, 100);
};
function finalcr() {
	current_progress = 70;
	var interval = setInterval(function() {
		if (current_progress < 97) {
			current_progress += 1;
		}
		$("#myBar").css("width", current_progress + "%").attr("aria-valuenow",
				current_progress).text(current_progress + "% Complete");
		if (current_progress >= 100)
			clearInterval(interval);
	}, 100);

};
function lastfinalcr(pid) {
	current_progress = 97;
	var interval = setInterval(function() {
		if (current_progress < 100) {
			current_progress += 1;
		}
		$("#myBar").css("width", current_progress + "%").attr("aria-valuenow",
				current_progress).text(current_progress + "% Complete");
		if (current_progress >= 100)
			clearInterval(interval);
	}, 100);
};
function relocatepg(pid) {
	setTimeout(function() {
		window.location.href = 'redirectgraphs/' + pid;
	}, 1000);
}

/* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Document.ready() begin <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< */
$(document).ready(function() {
	$("#run1").click(function(event) {
		event.preventDefault();
		
		var id = $("#proId").val();
		url = "/simulate";
		$.ajax({
			url : url,
			data : {
				pId : id
			},
			success : function(data) {
				console.log(data);
				if (data == "success1") {
					inputcr();
					geninput(id);
				} else {
					$("#loadtable").html(data);
					jQuery("#exampleModal").modal('show');
				}
			}
		});

	});

	$("#rl").click(function() {
		url = "/reservoirLithology/showLithology";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pid : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});

	});

	$('#ip').click(function() {
		var pid = $("#proId").val();
		url = "/injectionPlan/showinjection";
		$.ajax({
			url : url,
			data : {
				pid : pid
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$('#wd').click(function() {
		url = "/wellData";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pro_Id : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$('#pe').click(function() {
		url = "/pumping/showpump";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pid : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$('#fl').click(function() {
		var x = $("#proId").val();
		url = "/showFLFirstController";
		$.ajax({
			url : url,
			data : {
				pid : x
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$("#pp").click(function() {
		url = "/proppant/showproppant";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pid : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});

	});
	$("#wf").click(function() {
		url = "/wellforcast/showforcast";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pid : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$("#rfp").click(function() {
		url = "/reservoirFluid1/showreservoir1";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pid : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$("#sa").click(function() {
		url = "/stressanalysis/showstress";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pid : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$("#rp").click(function() {
		url = "/rockProperties/showrock";
		var id = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pid : id
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$("#if").click(function() {
		url = "/injectedfluid1/showfluid1";
		var x = $("#proId").val();
		$.ajax({
			url : url,
			data : {
				pro_Id : x
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

	$("#mf").click(function() {
		var x = $("#proId").val();
		url = "/miniFrac";
		$.ajax({
			url : url,
			data : {
				pro_Id : x
			},
			success : function(data) {
				$("#loadtable").html(data);
				jQuery("#exampleModal").modal('show');
			}
		});
	});

});

/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Document.ready() ENDS  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*/
