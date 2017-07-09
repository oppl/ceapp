declare namespace functx = "http://www.functx.com";
declare function functx:distinct-deep
  ( $nodes as node()* )  as node()* {

    for $seq in (1 to count($nodes))
    return $nodes[$seq][not(functx:is-node-in-sequence-deep-equal(
                          .,$nodes[position() < $seq]))]
 } ;
 declare function functx:is-node-in-sequence-deep-equal
  ( $node as node()? ,
    $seq as node()* )  as xs:boolean {

   some $nodeInSeq in $seq satisfies deep-equal($nodeInSeq,$node)
 } ;
let $course := doc('https://www.kusss.jku.at/kusss/select-course.action?curId=204&amp;schedule=true&amp;type=xml')//course
let $sepp := functx:distinct-deep((
for $c in $course
return
<lva>
<name>{$c/title/text()}</name>
<subjectType>{$c/type/text()}</subjectType>
<id>{xs:string($c/@id)}</id>
{$c//schedule/course-date}
{$c/term}
</lva>
))
return
<lvas>
{$sepp}
{$sepp/schedule}
</lvas>