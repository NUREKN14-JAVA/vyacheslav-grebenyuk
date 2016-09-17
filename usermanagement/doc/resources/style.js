/* ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- */
function replacePage(fid) {
	c = document.getElementById('content');
	l = window.parent.document.getElementById(fid);
	if (l != null) {
		l.innerHTML = c.innerHTML;
		c.innerHTML = '';
	}
}

/* ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- */
function tabbed(sid) {
	tabs = document.getElementById(sid).childNodes;
	for (i = 0; i < tabs.length; i++) {
		addlistener(tabs[i], 'mouseover', over);
		addlistener(tabs[i], 'mouseout', out);
		addlistener(tabs[i], 'mousedown', down);
	}
}

function over(event) { changeclass(event, 'tab', 'tabhov'); }

function out(event) { changeclass(event, 'tabhov', 'tab'); }

function down(event) {
	t = gettarget(event);
	tabs = t.parentNode.childNodes;
	pages = document.getElementById(t.parentNode.id + '_c').childNodes;
	for (i = 0; i < tabs.length; i++) {
		sel = (tabs[i] == t);
		tabs[i].className = sel ? 'tabsel' : 'tab';
		pages[i].className = sel ? '' : 'hidden';
	}
}

function changeclass(e, prev, curr) {
	t = gettarget(e);
	if (t.className == prev) t.className = curr;
}

function addlistener(target, type, listener) {
	if (target.addEventListener) target.addEventListener(type, listener, false);
	else target.attachEvent('on' + type, listener);
}

function gettarget(e) {
	return (e.target) ? e.target : e.srcElement;
}
/* ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- */
function showitem(t) { t.className = 'hoveritem'; }

function hideitem(t) { t.className = 'item'; }

/* ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- */
function more(t) {
	t.parentNode.lastChild.className='inline'; t.className='hidden'; return false;
}

/* ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- */
var indexloading = false;

function search(field, root) {
	if (typeof NAMES == 'undefined') {
		if (!indexloading) {
			indexloading = true;
			s = document.createElement('script');
			s.type = 'text/javascript'; s.src = root + '/resources/index.js';
			document.getElementsByTagName('head')[0].appendChild(s);
		}
		if (typeof NAMES == 'undefined') return;
	}
	hidepopup();
	if (field.value == '') return;

	t = field.value; e = null;
	for (i = 0, n = 0; i < NAMES.length; i++) {
		if (NAMES[i].indexOf(t) != 0) continue;
		if (n == 0) {
			e = document.createElement('p');
			e.id = 'popup'; e.className = 'result';
		}
		d = document.createElement('div');
		
		s = getlink(i, root) + ' &ndash; ' + TYPENAMES[TYPES[i]];
		if (PARENTS[i] != -1) s += ' ' + getlink(PARENTS[i], root);
		
		d.innerHTML = s;
		e.appendChild(d);
		n++; if (n > 8) break;
	}
	
	if (e != null) {
		x  = 0; y = field.offsetHeight + 1;
		for (c = field; c != null; c = c.offsetParent) {
			x += c.offsetLeft; y += c.offsetTop;
		}
		sh = document.body.scrollTop + document.body.clientHeight;
		e.style.position = 'absolute'; e.style.left = x; e.style.top = y;
		document.body.appendChild(e);
		if (y + e.offsetHeight > sh) {
			e.style.top = Math.max(0, y - field.offsetHeight - 2 - e.offsetHeight);
		}
	}
}

function indexloaded() {
	addlistener(document.body, "click", hidepopup);
	addlistener(window, "blur", hidepopup);
}

function getlink(i, root) {
	pi = PARAMETERS[i];
	s = '<a href="' + root + '/';
	
	if (TYPES[i] == 0) s += pkglink(i) + 'package-summary.html';
	else if (TYPES[i] < 7) s += pkglink(PARENTS[i]) + NAMES[i] + '.html';
	else {
		s += pkglink(PARENTS[PARENTS[i]]) + NAMES[PARENTS[i]] + '.html#' + NAMES[i];
		if (pi != -1) s += '(' + SIGNATURES[pi * 2] + ')';
	}
	
	s += '">' + NAMES[i];
	if (pi != -1) s += '(' + SIGNATURES[pi * 2 + 1] + ')';
	
	return s + '</a>';
}

function pkglink(i) {
	s = NAMES[i];
	while ((j = s.indexOf('.')) != -1) s = s.substr(0, j) + '/' + s.substr(j + 1);
	return s + '/';
}

function hidepopup() {
	p = document.getElementById('popup');
	if (p != null) document.body.removeChild(p);
}
