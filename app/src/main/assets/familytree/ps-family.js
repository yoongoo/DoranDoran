/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function($) {

    toJS_addMember = toAndroid_addMember;
    toJS_setfamilyTreeMode = toAndroid_setFamilyTreeMode;

    // 공유 데이터 
    var rootDiv = '';
    var treeGround = null;
    var newMemberForm = '';
    var memberName = '';
    var memberGender = '';
    var memberAge = '';
    var memberPic = '';
    var memberRelation = '';
    var familyTree = new Array();
    var memberId = 0;
    var selectedMember = null;// refrence to selected member
    var self = true;
    var memberSpace = 92;
    var memberWidth = 115;
    var memberHeight = 107;
    var memberDetails = null;
    var options_menu = null;
    var object = new Object();
    var rut = null;
    var parent = null;
    var mode = 'InsertMode';


    $.fn.pk_family = function(options) {
        if (rootDiv == null) {
            // error message in console
            jQuery.error('wrong id given');
            return;
        }
        rootDiv = this;
        init();
    }

    // function to create tree from json data
    // JSON 데이터를 기반으로 패밀리 트리를 생성
    $.fn.pk_family_create = function(options) {
        if (rootDiv == null) {
            // error message in console
            jQuery.error('wrong id given');
            return;
        }
        rootDiv = this;
        var settings = $.extend({
            // These are the defaults.
            data: "",
        }, options);
        // JSON 파싱
        var obj = jQuery.parseJSON(settings.data);
        // 패밀리 트리를 그릴 수 있는 판을 생성 
        addBreadingGround();
        // ul 태그로 계층을 나눔 
        parent = $('<ul>');
        // treeGround id 를 가지고 있는 div 태그에 하위 태그 넣기
        $(parent).appendTo(treeGround);
        // JSON 파싱한 데이터로 부터 자식 노드들을 찾아서 표시
        traverseObj(obj);

        // 새로운 노드 추가 클릭 시 표시할 폼 로딩
        createNewMemberForm();
        // 자세히 보기 클릭 시 표시할 폼 로딩
        member_details();
        // 컨텍스트 메뉴 생성 
        createOptionsMenu();
        document.oncontextmenu = function() {
            return false;
        };

    }
    function tempTest(obj) {
        for (var i in obj) {
            document.write(i + " &nbsp;");
            if (i.indexOf('a') > -1 && i.length == 2) {
                ;
            } else {
                tempTest(obj[i]);
            }
        }
        return;
    }

    // 데이터를 UI로 표시하는 핵심코드
    // @param obj : JSON 파싱 데이터
    function traverseObj(obj) {

  
        // li, ul, a 태그 순회
        for (var i in obj) {

            // li 태그일 때 
            if (i.indexOf("li") > -1) {
                // 자식 노드를 찾아서 
                var li = $('<li>');
                // 부모 ul 태그 아래에 넣기
                $(li).appendTo(parent);
                parent = li;
                // 자식 노드의 자식을 확장을 하기 위해 재귀적으로 호출 
                traverseObj(obj[i]);
                // 다시 부모로 이동
				parent = $(parent).parent();
            }

            // a 태그 일 때 
            if (i.indexOf("a") > -1 && i.length == 2) {
                // a 태그에 4가지 속성 부여 
                var link = $('<a>');
                link.attr('data-name', obj[i].name);
                link.attr('data-age', obj[i].age);
                link.attr('data-gender', obj[i].gender);
                link.attr('data-relation', obj[i].relation);

                // 배우자일 경우
				if(obj[i].relation == 'Spouse'){
					link.attr('class', 'spouse');
				}
                // 가운데 정렬 추가 
                var center = $('<center>').appendTo(link);
                // 이미지 추가
                var pic = $('<img>').attr('src', obj[i].pic);
                var extraData = "";
                // 남자일 경우 
                if (obj[i].gender == "Male") {
                    extraData = "(M)";
                } else  { // 여자일 경우 
                    extraData = "(F)";
                }
                // 이미지 가운데 정렬 
                $(pic).appendTo(center);
                // 개행 추가 
                $(center).append($('<br>'));
                // span 태그를 생성해서 이름과 추가 정보를 넣고 center 태그에 추가 
                $('<span>').html(obj[i].name + " " + extraData).appendTo(center);
                // 노드에 마우스 이벤트 추가 
                $(link).mousedown(function(event) {
                    // LBUTTONDOWN  
                    if (event.button == 0) {
                        // 컨텍스트 메뉴
                        //displayPopMenu(this, event);
                        //onFamilyTreeNodeClicked(obj[i].name);

                        toAndroid_onFamilyTreeNodeClicked(this, obj[i].name);
                        return false;
                    }
                    return true;
                });
                // 
                $(link).appendTo(parent);
            }

            // ul 태그를 찾을경우 
            if (i.indexOf("ul") > -1) {
                var ul = $('<ul>');
                $(ul).appendTo(parent);
                parent = ul;
                traverseObj(obj[i]);
                return;
            }
        }
        return;
    }

    // 서버에 JSON 데이터로 인코딩하여 전송 하기 위한 함수 
   $.send_Family =  $.fn.pk_family_send = function(options) {
        if (rootDiv == null) {
            // error message in console
            jQuery.error('wrong id given');
            return;
        }

        // 보낼 url을 추가 
        var settings = $.extend({
            // These are the defaults.
            url: "",
        }, options);

        // 현재 트리의 정보를 JSON 데이터로 인코딩 
        var data = createSendURL();
        data = data.replace(new RegExp(']', 'g'), ""); 
        data = data.replace(new RegExp('\\[', 'g'), ""); 
        console.log(data);

        // 서버로 비동기 호출 AJAX 통신 GET 방식으로 파라미터 전달 
        // ex) $_GET['tree'] 에 JSON 데이터 수신 
        $.ajax({
            url: settings.url + "?tree=" + data,
        }).done(function() {
            alert('completed');
        });
    }

    // JSON 데이터 인코딩 함수 
    function createSendURL() {
        // body 에서 루트 ul 을 찾음 
        rut = $(treeGround).find("ul:first");
        parent = object;
        // JSON 데이터으로 인코딩 
        object = createJson(rut);
        // 문자열 형태로 공백을 제거하고 최소화 
        return (JSON.stringify(object));

    }

    function createJson(root) {
        var thisObj = [];
        // ul 태그를 찾으면 
        if ($(root).prop('tagName') == "UL") {
            var item = {};
            var i = 0;
            // li 태그의 개수만큼 JSON 데이터로 생성 
            $(root).children('li').each(function() {
                item["li" + i] = createJson(this);
                i++;
            });
            // JSON 인코딩 데이터 저장 
            thisObj.push(item);
            return thisObj;
        }
        // li 태그를 찾으면 
        if ($(root).prop('tagName') == "LI") {
            var item = {};
            var i = 0;
            $(root).children('a').each(function() {
                var m = "a" + i;
                item[m] = {};
                item[m]["name"] = $(this).attr("data-name");
                item[m]["age"] = $(this).attr("data-age");
                item[m]["gender"] = $(this).attr("data-gender");
                try {
                    item[m]["relation"] = $(this).attr("data-relation");
                } catch (e) {
                    item[m]["relation"] = "self";
                }
                item[m]["pic"] = $(this).find('img:first').attr("src");
                i++;
            });

            if ($(root).find('ul:first').length) {
                parent = thisObj;
                item["ul"] = createJson($(root).find('ul:first'));
            }
            thisObj.push(item);
            return thisObj;
        }
    }
    function init() {
        // addMemberButton();
        addBreadingGround();
        createNewMemberForm();
        member_details();
        createOptionsMenu();
        displayFirstForm();
        document.oncontextmenu = function() {
            return false;
        };
    }

    function createOptionsMenu() {
        var div = $('<div>').attr('id', 'pk-popmenu');
        var ul = $('<ul>');
        // add member button
        var liAdd = $('<li>').html('Add Member').appendTo(ul);
        liAdd.click(function(event) {
            displayForm();
            $(options_menu).css('display', 'none');
        });
        // view member button
        var liDisplay = $('<li>').html('View Details').appendTo(ul);
        liDisplay.click(function(event) {
            displayData(selectedMember);
            $(options_menu).css('display', 'none');
        });
        // remove member button
        var liRemove = $('<li>').html('Remove Member').appendTo(ul);
        liRemove.click(function(event) {
            removeMember(selectedMember);
            $(options_menu).css('display', 'none');
        });
        // cancel the pop menu
        var liCancel = $('<li>').html('Cancel').appendTo(ul);
        liCancel.click(function(event) {
            //displayForm(this);
            $(options_menu).css('display', 'none');
        });
        $(div).append(ul);
        options_menu = div;
        $(options_menu).appendTo(rootDiv);

    }
    function createNewMemberForm() {
        var memberForm = $('<div>').attr('id', 'pk-memberForm');
        var cross = $('<div>').attr('class', 'pk-cross');
        $(cross).text('X');
        $(cross).click(closeForm);
        $(cross).appendTo(memberForm);
        var table = $('<table>').appendTo(memberForm);
        // name
        $('<tr>').html('<td><label>Name</label></td><td><input type="text" value="" id="pk-name"/></td>').appendTo(table);
        $('<tr>').html(' <td><label>Gender</label></td><td><select id="pk-gender"><option value="Male">Male</option><option value="Female">Female</option></select></td>').appendTo(table);
        $('<tr>').html('<td><label>Age</label></td><td><input type="text" value="" id="pk-age"></td>').appendTo(table);
        $('<tr>').html(' <td class="relations"><label>Relation</label></td><td class="relations"><select id="pk-relation">\n\\n\
<option value="Mother">Mother</option>\n\
<option value="Father">Father</option>\n\\n\
<option value="Sibling">Sibling</option>\n\\n\
<option value="Child">Child</option>\n\\n\
<option value="Spouse">Spouse</option>\n\\n\
</select></td>').appendTo(table);
        $('<tr>').html('<td><label>Upload Photo</label></td><td><input type="file" id="pk-picture"></td>').appendTo(table);
        var buttonSave = $('<input>').attr('type', 'button');
        $(buttonSave).attr('value', 'Save');
        $(buttonSave).click(saveForm);
        var td = $('<td>').attr('colspan', '2');
        td.css('text-align', 'center');
        td.append(buttonSave);
        $('<tr>').append(td).appendTo(table);
        newMemberForm = memberForm;
        $(newMemberForm).appendTo(rootDiv);
    }

    function member_details() {
        memberDetails = $('<div>').attr('id', 'pk-member-details');
        $(memberDetails).appendTo(rootDiv);
    }

    function closeForm() {
        $(newMemberForm).css('display', 'none');
    }

    function saveForm() {
        memberName = $('#pk-name').val();
        memberGender = $('#pk-gender').val();
        memberAge = $('#pk-age').val();
        memberPic = $('#pk-picture');
        memberRelation = $('#pk-relation').val();
        //clear exsiting data from form
        $('#pk-name').val('');
        $('#pk-age').val('');
        $('#pk-relation').val('');
        // after saving
        addMember();
        closeForm();
    }

    // 패밀리 트리를 표시할 수 있는 판을 생성 
    // jQuery Ui로 부터 드래그 할 수 있게 설정
    function addBreadingGround() {
        var member = $('<div>').attr('id', 'treeGround');
        $(member).attr('class', 'tree-ground');
        $(member).appendTo(rootDiv);
        treeGround = member;
        $(treeGround).draggable();
    }

    function addMemberButton() {
        var member = $('<input>').attr('type', 'button');
        $(member).attr('value', 'Add Member');
        $(member).click(displayForm);
        $(member).appendTo(rootDiv);
    }
    function displayForm(input) {
        $('.relations').css('display', '');
        $(newMemberForm).css('display', 'block');
    }
    function displayPopMenu(input, event) {
        if ($(options_menu).css('display') == 'none') {
            selectedMember = input;
            self = false;
            $(options_menu).css('display', 'block');
            $(options_menu).css('top', event.clientY);
            $(options_menu).css('left', event.clientX);
        }
    }
    function displayFirstForm() {
        selectedMember = null;
        self = true;
        $('.relations').css('display', 'none');
        $(newMemberForm).css('display', 'block');
        $('#pk-relation').val('Main');
    }
    function addMember() {
        var aLink = $('<a>').attr('href', '#');
        var center = $('<center>').appendTo(aLink);
        var pic = $('<img>').attr('src', 'images/profile.png');
        var extraData = "";

        if (memberGender == "Male") {
            extraData = "(M)";
        } else {
            extraData = "(F)";
            $(pic).attr('src', 'images/profile-f.png');
        }
        $(pic).appendTo(center);
        $(center).append($('<br>'));
        $('<span>').html(memberName + " " + extraData).appendTo(center);
        readImage(memberPic, pic);

        var li = $('<li>').append(aLink);
        $(aLink).attr('data-name', memberName);
        $(aLink).attr('data-gender', memberGender);
        $(aLink).attr('data-age', memberAge);
        $(aLink).attr('data-relation', memberRelation);
        $(aLink).mousedown(function(event) {
            if (event.button == 0) {
                //displayPopMenu(this, event);
                toAndroid_onFamilyTreeNodeClicked(this, memberName);
                return false;
            }
            return true;
        });
        var sParent = $(selectedMember).parent(); // super parent
        if (selectedMember != null) {
            if (memberRelation == 'Mother') {
				var parent = $(sParent).parent();
                var parentParent = $(parent).parent();
                var fatherElement = $(parentParent).find("a:first");

                if(fatherElement.length == 0){
					console.log('adding adajecent to father');
					console.log(fatherElement);
					var tmp = $(fatherElement).parent();
					$(aLink).attr('class', 'mother');
					$(tmp).append(aLink);
				
				}else{
					console.log('adding mother alone');
					var ul = $('<ul>').append(li);
					$(parent).appendTo(li);
					$(parentParent).append(ul);
				}
            }
            if (memberRelation == 'Spouse') {
                $(aLink).attr('class', 'spouse');
                var toPrepend = $(sParent).find('a:first');
                $(sParent).prepend(aLink);
                $(sParent).prepend(toPrepend);
            }
            if (memberRelation == 'Child') {
                var toAddUL = $(sParent).find('UL:first');
                if ($(toAddUL).prop('tagName') == 'UL') {
                    $(toAddUL).append(li);
                } else {
                    var ul = $('<ul>').append(li);
                    $(sParent).append(ul);
                }

            }
            if (memberRelation == 'Sibling') {
                $(sParent).parent().append(li);

            }
            if (memberRelation == 'Father') {
                var parent = $(sParent).parent();
                var parentParent = $(parent).parent();
                var ul = $('<ul>').append(li);
                $(parent).appendTo(li);
                $(parentParent).append(ul);
            }
        } else {
            var ul = $('<ul>').append(li);
            $(treeGround).append(ul);

        }
    }

// will show existing user info
    function displayData(element) {
        var innerContent = $('<table>');
        var content = '';
        var cross = $('<div>').attr('class', 'pk-cross');
        $(cross).text('X');
        $(cross).click(function() {
            $(memberDetails).css('display', 'none')
        });
        $(memberDetails).empty();
        $(cross).appendTo(memberDetails);
        content = content + '<tr><td>Name</td><td>' + $(element).attr('data-name') + '</td></tr>';
        content = content + '<tr><td>Age</td><td>' + $(element).attr('data-age') + '</td></tr>';
        content = content + '<tr><td>Gender</td><td>' + $(element).attr('data-gender') + '</td></tr>';
        if ($(element).attr('data-relation')) {
            content = content + '<tr><td>Relation</td><td>' + $(element).attr('data-relation') + '</td></tr>';
        } else {
            content = content + '<tr><td>Relation</td><td>Self</td></tr>';
        }
        content = content + '<tr><td colspan="2" style="text-align:center;"><img src="' + $(element).find('img').attr('src') + '"/></td></tr>';
        $(innerContent).html(content);
        $(memberDetails).append(innerContent);
        $(memberDetails).css('display', 'block');
    }
    function readImage(input, pic) {
        var files = $(input).prop('files');
        if (files && files[0]) {
            var reader = new FileReader();

            reader.onload = function(e) {
                $(pic).attr('src', e.target.result);
            }

            reader.readAsDataURL(files[0]);
        }
    }

    function removeMember(member) {
        if ($(member).attr('data-relation') == 'Sibling') {
            $(member).parent().remove();
        }
        if ($(member).attr('data-relation') == 'Child') {
            var cLen = $(member).parent().children('li').length;
            if (cLen > 1)
                $(member).remove();
            else {
                $(member).parent().remove();
            }
        }
        if ($(member).attr('data-relation') == 'Father') {
            var child = $(member).children('ul');
            var parent = $(member).parent().parent();
            $(child).appendTo(parent);
            $(member).remove();
        }
        if ($(member).attr('data-relation') == 'Spouse') {
            $(member).remove();
        }
    }


    function toAndroid_onFamilyTreeNodeClicked(paramMember, paramMsg) {
        toAndroid_SelectedMember(paramMember);

        if(mode == 'InsertMode') {
            window.to_Android.onFamilyTreeNodeClicked(paramMsg);
        }
        else if(mode == 'EditMode'){
            var name    = $(paramMember).attr('data-name');
            var age     = $(paramMember).attr('data-age');
            var gender  = $(paramMember).attr('data-gender');
            var relation= $(paramMember).attr('data-relation');
            var image   = 'no image';
            image       = $(paramMember).children('center')
                                        .children('img')
                                        .attr('src');

            window.to_Android.getFamilyTreeNodeInfo(name, age, gender, relation, image);
        }
    }


    function toAndroid_setFamilyTreeMode(options){
        if(options == 0){
            mode = 'InsertMode';
            $('#testString').html('추가 모드');
        }
        else if(options == 1){
            mode = 'EditMode';
            $('#testString').html('수정 모드');
        }
    }

    // 안드로이드에서 호출하는 함수
    function toAndroid_addMember(options){
        var aLink = $('<a>').attr('href', '#');
        var center = $('<center>').appendTo(aLink);
        var pic = $('<img>').attr('src', 'images/profile.png');
        var extraData = "";

        switch (options){
            case 0:
            case 2:
            case 4:
            case 6:
                memberGender ="Male";
                break;

            case 1:
            case 3:
            case 5:
            case 7:
                memberGender ="Female";
                break;
        }

        if (memberGender == "Male") {
            extraData = "(M)";
        } else {
            extraData = "(F)";
            $(pic).attr('src', 'images/profile-f.png');
        }

        $(pic).appendTo(center);
        $(center).append($('<br>'));
        $('<span>').html(memberName + " " + extraData).appendTo(center);
        readImage(memberPic, pic);

        var li = $('<li>').append(aLink);
        $(aLink).attr('data-name', '노드');
        $(aLink).attr('data-gender', 'Male');
        $(aLink).attr('data-age', '26');
        $(aLink).attr('data-relation', memberRelation);
        $(aLink).mousedown(function(event) {
            if (event.button == 0) {
                //displayPopMenu(this, event);


                toAndroid_onFamilyTreeNodeClicked(this, memberName);
                return false;
            }
            return true;
        });


        var sParent = $(selectedMember).parent(); // super parent

        if (selectedMember != null) {


            // 부
            if (options == 0) {
                var parent = $(sParent).parent();
                var parentParent = $(parent).parent();
                var ul = $('<ul>').append(li);
                $(parent).appendTo(li);
                $(parentParent).append(ul);
            }
        } else {
            var ul = $('<ul>').append(li);
            $(treeGround).append(ul);
        }

        // 모
        if (options == 1) {
            $('#testString').html('모 추가');
            var parent = $(sParent).parent();
            var parentParent = $(parent).parent();
            var fatherElement = $(parentParent).find("a:first");

            if(fatherElement.length == 0){
                console.log('adding adajecent to father');
                console.log(fatherElement);
                var tmp = $(fatherElement).parent();
                $(aLink).attr('class', 'mother');
                $(tmp).append(aLink);

            }else{
                console.log('adding mother alone');
                var ul = $('<ul>').append(li);
                $(parent).appendTo(li);
                $(parentParent).append(ul);
            }
        }

        // 배우자 ( 남편, 아내)
        if (options == 2 || options == 3) {
            $('#testString').html('배우자 추가');
            $(aLink).attr('class', 'spouse');
            var toPrepend = $(sParent).find('a:first');
            $(sParent).prepend(aLink);
            $(sParent).prepend(toPrepend);
        }


        // 형제
        if (options == 4 || options == 5) {
            $('#testString').html('형제 추가');
            $(sParent).parent().append(li);

        }




            // 자식(아들, 딸)
            if (options == 6 || options == 7) {
                $('#testString').html('자식 추가');
                var toAddUL = $(sParent).find('UL:first');
                if ($(toAddUL).prop('tagName') == 'UL') {
                    $(toAddUL).append(li);
                } else {
                    var ul = $('<ul>').append(li);
                    $(sParent).append(ul);
                }

            }




    }


    function toAndroid_SelectedMember(paramMember){
        selectedMember = paramMember;
        self = false;
    }

}(jQuery));
