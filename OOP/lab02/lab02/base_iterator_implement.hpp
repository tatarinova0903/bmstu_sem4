#ifndef BASE_ITERATOR_IMPLEMENT_H
#define BASE_ITERATOR_IMPLEMENT_H

#include "base_iterator.h"

using namespace iterator;

template<typename T>
base_iterator<T>::base_iterator(const base_iterator<T> &iter)
{
    this->ptr = iter.ptr;
}

template<typename T>
base_iterator<T>::base_iterator(shared_ptr<Node<T>> ptr)
{
    this->ptr = ptr;
}

template<typename T>
base_iterator<T>::base_iterator(weak_ptr<Node<T>> ptr)
{
    this->ptr = ptr;
}

template<typename T>
base_iterator<T>::~base_iterator()
{
    this->ptr.reset();
}

template<typename T>
base_iterator<T>& base_iterator<T>::operator =(const base_iterator<T>& iter)
{
    if (this != &iter)
        this->ptr = iter.ptr;
    return *this;
}

template<typename T>
base_iterator<T>& base_iterator<T>::operator ++()
{
    time_t t_time;
    t_time = time(NULL);
    if (!this->ptr.expired())
        throw iterator_exception(__FILE__, typeid(*this).name(), __LINE__, ctime(&t_time), "Invalid iterator");
    this->ptr = this->ptr.lock()->get_next();
    return *this;
}

template<typename T>
base_iterator<T> base_iterator<T>::operator ++(int)
{
    base_iterator tmp = *this;
    this->operator++;
    return tmp;
}

template<typename T>
base_iterator<T>::operator bool() const
{
    return !this->ptr.expired();
}

template<typename T>
bool base_iterator<T>::operator ==(const base_iterator<T>& iter) const
{
    return (this->ptr.lock() == iter.ptr.lock());
}

template<typename T>
bool base_iterator<T>::operator !=(const base_iterator<T>&iter) const
{
    return !(this->ptr.lock() == iter.ptr.lock());

}

#endif // BASE_ITERATOR_IMPLEMENT_H
