package hjkim.miniprj.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import hjkim.miniprj.domain.Member;
import hjkim.miniprj.domain.MemberSearch;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        if (member.getId()==null) {
            em.persist(member);
        } else {
            em.merge(member);
        }
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }


    public void deleteOne(Long id) {
        Member member = em.find(Member.class, id);

        if (member != null) {
            em.remove(member);
        }
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                 .getResultList();
    }


    public List<Member> findById(Long id) {
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                 .setParameter("id", id)
                 .getResultList();
    }    

    public List<Member> findByRsdRegistNum(String rsdRegistNum) {
        return em.createQuery("select m from Member m where m.rsdRegistNum <> '' and m.rsdRegistNum = :rsdRegistNum", Member.class)
        .setParameter("rsdRegistNum", rsdRegistNum)
        .getResultList();
        }

    public List<Member> findByBizNum(String bizNum) {
        return em.createQuery("select m from Member m where m.bizNum <> '' and m.bizNum = :bizNum", Member.class)
        .setParameter("bizNum", bizNum)
        .getResultList();
        }

    public List<Member>findAllByString(MemberSearch memberSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> o = cq.from(Member.class);

        
        List<Predicate> criteria = new ArrayList<>();

        // 회원유형 검색
        if (memberSearch.getMemberType() != null) {
            Predicate memberType = cb.equal(o.get("memberType"), memberSearch.getMemberType());
            criteria.add(memberType);
        }

        //회원 이름 검색
        if (StringUtils.hasText(memberSearch.getMemberName())) {
            Predicate name = cb.like(o.<String>get("name"), "%"+memberSearch.getMemberName()+"%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Member> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();
    }
}