/*
 * Developed by R. E. Bryant, 2017
 * Extended to store strings, 2018
 */

/*
 * This program implements a queue supporting both FIFO and LIFO
 * operations.
 *
 * It uses a singly-linked list to represent the set of queue elements
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "harness.h"
#include "queue.h"

/*
  Create empty queue.
  Return NULL if could not allocate space.
*/
queue_t *q_new()
{
    queue_t *q =  malloc(sizeof(queue_t));//frees memory space for the queue
    /* What if malloc returned NULL? */
    if(q==NULL)//in case malloc returns false
    {
      return false;
    }
    q->head = NULL;
    q->tail = q->head;
    q->size=0;
    return q;
}

/* Free all storage used by queue */
void q_free(queue_t *q)
{


  if(q==NULL)//base case
  {
    return;
  }
  if(q->head==NULL)
  {
    free(q);
    return;
  }
  list_ele_t *curr=q->head;
    /* How about freeing the list elements and the strings? */
    while(curr!=NULL)//go therough list and free all node
    {
      list_ele_t *temp=curr;
      char* temps=curr->value;
      curr=curr->next;
      free(temps);
      free(temp);
    }
    /* Free queue structure */
    free(q);//frees the queue strucuture
}

/*
  Attempt to insert element at head of queue.
  Return true if successful.
  Return false if q is NULL or could not allocate space.
  Argument s points to the string to be stored.
  The function must explicitly allocate space and copy the string into it.
 */
bool q_insert_head(queue_t *q, char *s)
{
    list_ele_t *newh;//create a new head element

    /* What should you do if the q is NULL? */
    if(q==NULL)//if there is not queue
    {
      return false;
    }



    newh = malloc(sizeof(list_ele_t));//allocate memory for list element it
    /* Don't forget to allocate space for the string and copy it */
    if(newh==NULL)//if malloc doesnt work
    {
      return false;
    }
    newh->value=malloc(strlen(s)+1);//length of s value is
    //allocated +1 for null terminator
    if(newh->value==NULL)//if malloc is is null
    {
      free(newh);//frees the previous node made as well
      return false;
    }

    /* What if either call to malloc returns NULL? */

    strcpy(newh->value,s);//place a copy of s into value
    if(q->size==0)//if there is nothing in then list
    {
      newh->next = q->head;//head equals null so setting newh

      q->head = newh;//making head equal new node placed in the front
        q->tail=q->head;//tail is head becuase there is one element
        q->size++;
      return true;
    }
    if(q->head==q->tail)//if there is one elemnt in the array
    {
        newh->next = q->head;//making head next

        q->head = newh;//making head equal new
          q->tail=q->head->next;//tail is the next
          q->size++;
        return true;
    }

    newh->next = q->head;//making newh the front
    q->head = newh;//making head equal new
    q->size++;//increment size counter
    return true;
}


/*
  Attempt to insert element at tail of queue.
  Return true if successful.
  Return false if q is NULL or could not allocate space.
  Argument s points to the string to be stored.
  The function must explicitly allocate space and copy the string into it.
 */
bool q_insert_tail(queue_t *q, char *s)
{
  list_ele_t *newt;
    /* You need to write the complete code for this function */
    /* Remember: It should operate in O(1) time */
  if(q==NULL)//if there is no queue
  {
    return false;
  }
  if(q->head==NULL)//if there is not value let insert at front do it
  {
    bool i= q_insert_head(q,s);//already implemented in other function
    //code reuse from 445
    return i;
  }
  newt= malloc(sizeof(list_ele_t));//allocate memory for list element it
  /* Don't forget to allocate space for the string and copy it */
  if(newt==NULL)//if malloc is null
  {
    return false;
  }
  newt->value=malloc(strlen(s)+1);//length of s value is
  //allocated +1 for null terminator
  if(newt->value==NULL)//if malloc is null
  {
    free(newt);//frees node made
    return false;
  }
strcpy(newt->value,s);//copy
//if(q->size==0)
//{
  //bool i=q_insert_head(q,s);
  //return i;
//}

if(q->head==q->tail)//if there is one element
{
  //list_ele_t *temp=q->tail;
  newt->next=NULL;//since it is the end of the list
  q->tail=newt;//tail is newt
  q->head->next=q->tail;//head is first tail is sexond
  q->size++;
  return true;
  //q->head->next=newt;
}

q->tail->next=newt;//makes last elemnt newt
q->tail=newt;//tail equals last element
newt->next=NULL;//since it is last
q->size++;//increm

return true;
}

/*
  Attempt to remove element from head of queue.
  Return true if successful.
  Return false if queue is NULL or empty.
  If sp is non-NULL and an element is removed, copy the removed string to *sp
  (up to a maximum of bufsize-1 characters, plus a null terminator.)
  The space used by the list element and the string should be freed.
*/
bool q_remove_head(queue_t *q, char *sp, size_t bufsize)
{

    /* You need to fix up this code. */
list_ele_t *temp;
char *temps;
if(q==NULL)
{
  return false;
}
if((q->size==0))
{
  return false;
}
if(sp==NULL)
{
  return false;
}
temp=q->head;//address of head stored into temp
temps=q->head->value;
strncpy(sp,temps,bufsize-1);//strncpy up to bufsize-1
 sp[bufsize-1]='\0';//puts null terminator into sp
if(q->tail==q->head)//in case its only one list element
{
  q->tail=q->head->next;//makes it null
}

q->head->value=NULL;//value is null
q->head=q->head->next;//head equals the  address of head.next gets rid of first value

free(temp);//freed the memory of head not that it equals head.next
free(temps);//freed space from string value
if((q->size)>0)//cant have negative elementse
{
  q->size--;
}
  return true;
}

/*
  Return number of elements in queue.
  Return 0 if q is NULL or empty
 */
int q_size(queue_t *q)
{
    /* You need to write the code for this function */
    /* Remember: It should operate in O(1) time */
    if(q==NULL)//incase there is nothing
    {
      return 0;
    }
    return q->size;
}

/*
  Reverse elements in queue
  No effect if q is NULL or empty
  This function should not allocate or free any list elements
  (e.g., by calling q_insert_head, q_insert_tail, or q_remove_head).
  It should rearrange the existing ones.
 */
void q_reverse(queue_t *q)
{

    if(q==NULL)
    {
      return;
    }
    //if(q->size==0)
    //{
      //return;
    //}
    if(q->size==1)
    {
      return;
    }
    list_ele_t *head=q->head;//keep track of orignal head becasue it gets lost
    //based on gordun lu reciation
    list_ele_t *curr=q->head;//make curr to iterate through list
    list_ele_t *next=NULL;//next
    list_ele_t *prev=NULL;//and curr
    while(curr!=NULL)
    {
      //goes through list and flips
      next=curr->next;//next equals next elemtn in list
      curr->next=prev;//equals null at first used to flip elements
      prev=curr;//prev not equals head at first
      curr=next;//iterates through list
    }
    //list_ele_t *temp=q->head;
    q->head=q->tail;//head and tail get lost
    q->tail=q->head;//head and tail get lost
    q->head=prev;//head last element
    q->tail=head;//tail is original head



}
