import styled from 'styled-components';
import Header from '../components/header';
import Footer from '../components/footer';
import { BoardSNS } from '../components/board-sns';


const Wrapper = styled.div`
  width: 100%;
  height: 100vh;
  padding-top: 120px;
  gap: 50px;
`;

const WrapperBox = styled.div`
  width: 100%;
  max-width: 1400px;
  height: auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 50px;
  padding: 100px 50px 200px 50px;
  /* padding-bottom: 200px; */
  margin: auto;
  position: relative;
  `;

export default function Board() {

    return(
        <Wrapper>
            <Header></Header>
            <WrapperBox>
              <BoardSNS />
            </WrapperBox>
            <Footer></Footer>
        </Wrapper>
    );
}